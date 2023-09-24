<?php
/**
 * @file    CMysqlPdo.php
 * @brief   数据库操作类（使用PDO）
 * @author  wangwei
 * @date    2015-4-6
 * @version v1.0
 * @note
 *     2015-4-6 新建文件
 *
 * Copyright (C):
 *  安徽中新软件有限公司
 *  AnHui ZXSoft Co.,Ltd.
*/
class CMysqlPdo {
    // PDO操作实例
    protected $pdo = null;
    // sql
    protected $sqlMeta = array('sql'=>'', 'params'=>array());
    // 数据库连接参数配置
    protected $config = array(
        'db_type'   => 'mysql',
        'host'      => 'localhost',
        'port'      => 3306,
        'username'  => 'root',
        'password'  => '123456',
        'db_name'   => 'peterclub',
        'charset'   => 'utf8',  // or utf8

        'options' => array(
            // 保留数据库驱动返回的列名
            PDO::ATTR_CASE => PDO::CASE_NATURAL,
            // 在获取数据时将空字符串转换成 SQL 中的 NULL
            PDO::ATTR_ORACLE_NULLS => PDO::NULL_NATURAL,
            // 转换数值为字符串
            PDO::ATTR_STRINGIFY_FETCHES => false,
        ),
    );

    /**
     * 构造函数
     * @param array $config 数据库配置
     */
    public function __construct($config = array()) {
        if (is_array($config)) {
            $this->config = array_merge($this->config, $config);
        }

        if (!extension_loaded('pdo')) {
            throw new Exception('Need PHP extension: PDO', 500);
        }

        if (!extension_loaded('pdo_' . $this->config['db_type'])) {
            throw new Exception('Need PHP extension: pdo_' . $this->config['db_type'], 500);
        }
    }

    /**
     * $sql     = "SELECT * FROM book WHERE id=:id";
     * $params  = array('id' => 200);
     * $pdo->query($sql, $params);
     *
     * exec("LOCK TABLE {tableName} WRITE")    // 锁定表
     * exec("UNLOCK TABLES")   // 解除表锁定
     */

    /**
     * 插入数据
     * @param  string  $table 表名
     * @param  array   $data  插入数据，格式 array('fieldName' => $value)
     * @return bool or 返回插入记录的ID，执行失败返回false
     */
    public function insert($table, array $data) {
        if (empty($table) || empty($data)) {
            return false;
        }

        $keys   = '';
        $marks  = '';
        $values = array();
        foreach($data as $key => $val) {
            $keys  .= "`{$key}`,";
            $marks .= ":_{$key}_,";
            $values[":_{$key}_"] = $val;
        }
        $keys = trim($keys, ',');
        $marks = trim($marks, ',');
        $result = $this->exec("INSERT INTO `{$table}` ( {$keys} ) VALUES ( {$marks} )", $values);
        // 返回插入记录的ID
        $id = $this->pdo->lastInsertId();
        if ($id) {
            return $id;
        } else {
            return $result;
        }
    }

    /**
     * 更新数据
     * @param  string  $table 表名
     * @param  array   $data  需要更新的数据，格式 array('fieldName' => $value)
     * @param  string  $where 条件
     * @return bool or int 返回影响的行数，执行失败返回false，数据未修改时返回0
     */
    public function update($table, array $data, $where) {
        if (empty($table) || empty($data) || empty($where)) {
            return false;
        }

        $keys   = '';
        $values = array();
        foreach ($data as $key => $val) {
            $keys .= "`{$key}`=:_{$key}_,";
            $values[":_{$key}_"] = $val;
        }
        $keys = trim($keys, ',');

        return $this->exec("UPDATE `{$table}` SET {$keys} WHERE {$where}", $values);
    }

    /**
     * 删除数据
     * @param  string  $table 表名
     * @param  string  $where 条件
     * @return bool or 返回影响的行数，执行失败返回false
     */
    public function delete($table, $where) {
        if (empty($table) || empty($where)) {
            return false;
        }

        return $this->exec("DELETE FROM `{$table}` WHERE {$where}");
    }

    /**
     * 获取一条记录
     * @param  string  $table 表名
     * @param  string  $where 条件
     * @return bool or array  执行失败返回false
     */
    public function findOne($table, $where = '') {
        if (empty($table)) {
            return false;
        }

        $where = empty($where) ? '' : 'WHERE ' . $where;
        $data = $this->query("SELECT * FROM `{$table}` {$where} LIMIT 1");
        return false !== $data && isset($data[0]) ? $data[0] : false;
    }

    /**
     * 获取所有记录
     * @param  string  $table 表名
     * @param  string  $where 条件
     * @return bool or array  执行失败返回false
     */
    public function findAll($table, $where = '') {
        if (empty($table)) {
            return false;
        }

        $where = empty($where) ? '' : 'WHERE ' . $where;
        $data = $this->query("SELECT * FROM `{$table}` {$where} ");
        return false !== $data && isset($data[0]) ? $data : false;
    }

    /**
     * 获取记录总数
     * @param  string $table 表名
     * @param  string $where 条件
     * @return int    记录总数
     */
    public function count($table, $where = '') {
        if (empty($table)) {
            return false;
        }
        $where = empty($where) ? '' : 'WHERE ' . $where;
        $data = $this->query("SELECT COUNT(*) AS _total_ FROM `{$table}` {$where}");
        return false !== $data && isset($data[0]['_total_']) ? $data[0]['_total_'] : 0;
    }

    /**
     * 执行SQL，返回结果集
     * @param  string $sql    SQL语句
     * @param  array  $params 参数绑定
     * @return bool or array  执行失败返回false
     * @example
     *     $sql = "INSERT INTO sys_log (`zt`, `kt`, `action`, `result`, `type`, `add_time`) VALUES (:zt, :kt, :action, :result, :type, :add_time)";
     *     $params = array(
     *         ':zt'       => $zt,
     *         ':kt'       => $kt,
     *         ':action'   => $action,
     *         ':result'   => $result,
     *         ':type'     => $type,
     *         ':add_time' => time(),
     *     );
     *     $data = $db->query($sql, $params);
     */
    public function query($sql, array $params = array()) {
        $this->pdo = $this->_connect();
        $sth = $this->_bindParams($sql, $params);
        if (false === $sth) {
            return false;
        }

        $result = $sth->execute();
        if (false === $result) {
            return false;
        }

        $data = $sth->fetchAll(PDO::FETCH_ASSOC);
        return $data;
    }

    /**
     * 执行SQL，返回影响的行数
     * @param  string $sql    SQL语句
     * @param  array  $params 过滤参数（使用命名占位符 :name）
     * @return bool or int    执行失败返回false
     */
    public function exec($sql, array $params = array()) {
        $this->pdo = $this->_connect();
        $sth = $this->_bindParams($sql, $params);
        if (false === $sth) {
            return false;
        }

        $result = $sth->execute();
        if (false === $result) {
            return false;
        }

        // 返回影响记录的行数
        return $sth->rowCount();
    }


    /**
     * 获取最后执行sql
     * @return string
     */
    public function getSql() {
        $sql = $this->sqlMeta['sql'];
        $params = $this->sqlMeta['params'];
        foreach($params as $key => $val) {
            if (is_int($key)) {
                // 问号占位符 ?
                $temp = explode('?', $sql);
                $sql = '';
                foreach ($temp as $key => $val) {
                    if (!isset($params[$key])) {
                        $params[$key] = '';
                    }
                    $sql .= $val . $this->pdo->quote($params[$key]);
                }
                $sql = substr($sql, 0, -2); // 去掉末尾2个字符 "''"
                break;
            } else {
                // 命名占位符 :name
                $sql = str_replace(":{$key}", $this->pdo->quote($val), $sql);
            }
        }
        return $sql;
    }


    /**
     * 事务开始
     */
    public function beginTransaction() {
        return $this->pdo->beginTransaction();
    }

    /**
     * 事务提交
     */
    public function commit() {
        return $this->pdo->commit();
    }

    /**
     * 事务回滚
     */
    public function rollBack() {
        return $this->pdo->rollBack();
    }

    /**
     * pdo prepare and bindValue
     * @param  string $sql    SQL语句
     * @param  array  $params 过滤参数（使用命名占位符 :name）
     * @return bool or PDOStatement  执行失败返回false
     */
    protected function _bindParams($sql, array $params) {
        $this->sqlMeta = array('sql'=>$sql, 'params'=>$params);
        $sth = $this->pdo->prepare($sql);
        if (false === $sth) {
            return false;
        }

        foreach($params as $key => $val) {
            $sth->bindValue($key, $val);
        }

        return $sth;
    }

    /**
     * mysql connect
     * @return PDO
     */
    protected  function _connect() {
        // init connect, lazy do
        if ($this->pdo) {
            return $this->pdo;
        }

        $config = $this->config;
        $pdo = null;
        $error = '';
        $dsn = 'mysql:host=' . $config['host'] . ';port=' . $config['port'] . ';dbname=' . $config['db_name'] . ';charset=' . $config['charset'];
        try {
            $pdo = new PDO($dsn, $config['username'], $config['password'], $config['options']);
            $pdo->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);  // 禁用预处理语句的模拟, 防注入
        } catch(PDOException $e) {
            $error = $e->getMessage();
        }

        if(!$pdo){
            throw new Exception('database connect error : '.$error, 500);
        }
        $pdo->exec("SET NAMES {$config['charset']}");

        return $pdo;
    }

    public function __destruct() {
        if ($this->pdo){
            $this->pdo = null;
        }
    }
}
?>