$(function(){
    let cookie;
    //verification of the cookie
    /*
    $.ajax({
        url:'/ubifeed',
            data:{action:'verification'},
            type:'POST',
            success: function(response){
                if(response != ""){
                    cookie = response;
                    if(cookie.role = "restaurant"){
                        $('#restaurant').show();
                    }
                    else{
                        $('#pickup_station').show();
                    }

                }
                else{
                    $('#connection').show();
                    $('#authentication-restaurant').show()
                }
            }
    });
    */
    function hideShowButtonInit(button) {
        let parentTable = button.closest('table');
        let nextRow = button.parent().parent().index()+1;
        let nextTr = parentTable.find('tr:eq('+nextRow+')');
        
        nextTr.is(':hidden') ? nextTr.show() : nextTr.hide();
    }
    
    $('#restaurant_log_in').on('click', function(){
        let mail = $('#restaurant_mail').val();
        let password = $('#restaurant_pswd').val();
        
        /*$.ajax({
            url:'/ubifeed',
            data:{action:'login-restaurant',mail:mail,password:password},
            type:'POST',
            success: function(reponse){
                switch(parseInt(reponse)){
                    case 0:
                        cookie.mail=mail;
                        cookie.role="restaurant";
                        $('#restaurant').show();
                        break;
                    case 1:
                        $('#alert').text("Email or password incorrect");
                        break;
                }
            }
        });
        */
       $('#connection').hide();
       $('#restaurant').show();
    });

    $('#station_log_in').on('click', function(){
        let mail = $('#station_mail').val();
        let password = $('#station_pswd').val();
        /*$.ajax({
            url:'/ubifeed',
            data:{action:'login-station',mail:mail,password:password},
            type:'POST',
            success: function(reponse){
                switch(parseInt(reponse)){
                    case 0:
                        cookie.mail=mail;
                        cookie.role="station";
                        $('#pickup_station').show();
                        break;
                    case 1:
                        $('#alert').text("Email or password incorrect");
                        break;
                }
            }
        });
        */
        $('#connection').hide();
        $('#pickup_station').show();
    });

    $('#to_station').on('click', function(){
        $('#alert').text("");
        $('#authentication-restaurant').hide()
        $('#authentication-station').show()
    });
    $('#to_restaurant').on('click', function(){
        $('#alert').text("");
        $('#authentication-station').hide()
        $('#authentication-restaurant').show()
    });
    $('.log_out').on('click', function(){
        $('#alert').text("");
        $('#connection').show();
        $('#restaurant').hide()
        $('#pickup_station').hide()
    })
    $('.hide_show').on('click', function(){
        hideShowButtonInit($(this))
    })


    
});