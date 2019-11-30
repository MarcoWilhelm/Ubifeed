$(function(){
    let cookie;
    //verification of the cookie
    $.ajax({
        url:'localhost:8080/ubifeed/',
            data:{action:'verification'},
            type:'POST',
            success: function(response){
                if(response != ""){
                    cookie = response;
                    if(cookie["role"] = "restaurant"){
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
    function hideShowButtonInit(button) {
        let parentTable = button.closest('table');
        let nextRow = button.parent().parent().index()+1;
        let nextTr = parentTable.find('tr:eq('+nextRow+')');
        
        nextTr.is(':hidden') ? nextTr.show() : nextTr.hide();
    }

    function addInfoCookie(response, role){
        cookie["id"]=parseInt(response);
        cookie["role"]= role;
    }
    
    $('#restaurant_log_in').on('click', function(){
        console.log("login restaurant")
        let mail = $('#restaurant_mail').val();
        let password = $('#restaurant_pswd').val();
        
        $.ajax({
            url:'localhost:8080/ubifeed',
            data:{action:'login-pickup-station',mail:mail,password:password},
            type:'POST',
            success: function(reponse){
                if(response == null)
                    $('#alert').text("Email or password incorrect")
                else{
                    console.log(response,)//addInfoCookie(response, role)
                    //$('#connection').hide();
                    //$('#restaurant').show();
                } 
            }
        });
       
    });

    $('#station_log_in').on('click', function(){
        console.log("login station")
        let mail = $('#station_mail').val();
        let password = $('#station_pswd').val();
        let role = "station";
        $.ajax({
            url:'localhost:8080/ubifeed',
            data:{action:'login-pickup-station',mail:mail,password:password},
            type:'POST',
            success: function(reponse){
                if(response == null){
                    $('#alert').text("Email or password incorrect")
                }else{
                    console.log(response)//addInfoCookie(response, role)
                    //$('#connection').hide();
                    //$('#pickup_station').show();
                }
            }
        });
        
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