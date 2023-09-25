<?php

/**
 * 数据库操作类
 * User: youwenzhang
 * Date: 17/7/25
 * Time: 上午11:52
 */
require_once APP_PATH."include/mysql.php";
require_once APP_PATH."include/mysqlii.php";
class dbo
{
    private $db;

    function __construct()
    {
        switch (MYSQLCONN) {
            case 'mysqli':
                $this->db = MySqlii::getInstance();
                break;
            case 'mysql':
            default :
                $this->db = MySql::getInstance();
                break;
        }
    }

    /**
     * 向数据表中插入数据，成功返回自增的ID
     * @param $add_data_arr
     * @param $table
     * @return int
     */
    function insert_to_table(&$add_data_arr, $table)
    {
        if(!is_array($add_data_arr)){
            return 0;
        }

        $strsql = 'INSERT INTO `'.$table.'` ';
        $keys='';
        $values='';
        foreach($add_data_arr as $key=>$value){
            $keys .= '`'.$key.'`,';
            $values .= $value == 'NOW()' ? $value.',' : '\''.$value.'\','; //修正NOW()语句
        }
        $keys    = rtrim($keys,',');
        $values  = rtrim($values,',');
        $strsql .= ' ('.$keys.') VALUES ('.$values.')';


        $ret = $this->db->query($strsql);
        $insert_id = $this->db->insert_id();

        return $insert_id;
    }

    /**
     * 取得一行记录，通过一个字段
     * @param $key
     * @param $val
     * @param $table
     * @return array
     */
    function find_one_from_table($key, $val, $table)
    {
        $sql = "SELECT * FROM " . $table . " WHERE ".$key." = '".$val."' LIMIT 1";
        $res = $this->db->query($sql);
        $row = $this->db->fetch_array($res);
        return $row;
    }

}