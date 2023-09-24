<?php
/**
 * 订餐功能
 * User: kunge
 * Date: 2017/8/7
 * Time: 10:49
 */


/**
 * 获取和商品
 * @param null $shop_number
 * @return array
 */
function get_goods($shop_number = null, &$dbo){

}

/**
 * 获取商品类别
 * @return array
 */
function get_goods_class(&$dbo){
    $sql = "SELECT * FROM goods_class ORDER BY sort ASC ";
    $goods_class = $dbo->query($sql);
    $goods_class = $goods_class?$goods_class:[];
    return $goods_class;
}

/**
 * 根据商品类别获取商品
 * @return array
 */
function get_goods_by_class($goods_class_id, &$dbo){
    $shop_id = '0';
    $sql = "SELECT g.goods_id,g.goods_number,g.goods_name,g.goods_class_id,g.is_all_supply,g.sell_unit_id,g.goods_unit_price,g.goods_img,";
    $sql .= " u.sell_unit_name FROM goods AS g LEFT JOIN goods_sell_unit AS u ON g.sell_unit_id = u.sell_unit_id";
    $sql .= " WHERE (goods_class_id = {$goods_class_id}) AND ((is_all_supply = 1) OR (is_all_supply = 0 OR find_in_set('{$shop_id}',supply_shop_ids))) ";
    $sql .= " ORDER BY sort ASC ";
    $goods = $dbo->query($sql);
    $goods = $goods?$goods:[];
    return $goods;
}