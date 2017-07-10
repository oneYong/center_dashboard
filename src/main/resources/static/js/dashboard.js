/**
 * Created by WYKIM on 2017-03-29.
 */
//Ajax 공통 모듈
var Ajax = {
    submit : function(p_url, p_type, p_param, p_success_msg, p_fail_msg, p_action){
        $.ajax({
            url : p_url,
            type : p_type,
            dataType : "json",
            data: p_param,
            success : function(data) {
                if (data.code == 200) {
                    alert(p_success_msg);
                    p_action.ajax.reload(function(){
                        //callback...
                    });
                }else if(data.code == 400) {
                    alert(p_fail_msg);
                    p_action.ajax.reload(function(){
                        //callback...
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                return false;
            }
        });
    }
}

var DoAction = {
    init : function(p_url,p_param,p_action,p_datatype){
        var v_datatype = "json";
        if(p_datatype!=undefined){
            v_datatype = p_datatype;
        }
        this.comunication(p_url,p_param,v_datatype,p_action);

    },
    comunication : function(p_url,p_param,p_datatype,p_action){
        $.ajax({
            url : p_url,
            type : "POST",
            dataType : p_datatype,
            data: p_param,
            success : function(data) {
                if(p_datatype=="json"){
                    if (data.code == 200) {
                        DoAction.action(data,p_action,p_param);

                    }else if(data.code == 400) {
                        alert(data.msg);
                    }
                }else if(p_datatype=="html"){
                    DoAction.action(data,p_action);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                return false;
            }
        });
    },
    action : function(p_data,p_action,p_param){
        eval(p_action);
    }
}