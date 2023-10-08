function strToInt(str) {
    str = $.trim(str);
    if(str == "") return "";
    str = parseInt(str);
    if(isNaN(str)) {
        return 0;
    } else {
        return str;
    }
}