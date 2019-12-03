$(function(){
    let cookie = {};
    let orders = {};
    let timeout = null;
    //verification of the cookie
    console.log("Before Ajax");
    $.ajax({
        url:'/ubifeed/',
            data:{action:'verification'},
            type:'POST',
            success: function(response){
                console.log("verification");
                if(response != ""){
                    cookie = response;
                    console.log(cookie);
                    console.log(cookie["role"])
                    if(cookie["role"] == "restaurant"){
                        $('#restaurant').show();
                        $('#connection').hide();
                        $('#pickup_station').hide();
                        setRestTable();
                    }
                    else{
                        $('#pickup_station').show();
                        $('#restaurant').hide()
                        $('#connection').hide();
                        setStationTable();
                    }

                }
                else{
                    $('#connection').show();
                    $('#restaurant').hide()
                    $('#authentication-restaurant').show()
                    clearTimeout(timeout);
                }
            }
    });
    
    $('#restaurant_log_in').on('click', function(){
        let email = $('#restaurant_mail').val();
        let password = $('#restaurant_pswd').val();
        let role = "restaurant"
        $.ajax({
            url:'/ubifeed',
            data:{action:'login-restaurant',email:email,password:password},
            type:'POST',
            success: function(response){
                if(response == null)
                    $('#alert').text("Email or password incorrect")
                else{
                    addInfoCookie(response["restaurantId"], role)
                    $('#connection').hide();
                    $('#restaurant').show();
                    setRestTable();
                } 
            }
        });
       
    });

    $('#station_log_in').on('click', function(){
        let email = $('#station_mail').val();
        let password = $('#station_pswd').val();
        let role = "station";
        $.ajax({
            url:'/ubifeed',
            data:{action:'login-pickup-station',email:email,password:password},
            type:'POST',
            success: function(response){
                if(response == null){
                    $('#alert').text("Email or password incorrect")
                }else{
                    addInfoCookie(response, role)
                    $('#connection').hide();
                    $('#pickup_station').show();
                    setStationTable();
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
        $.ajax({
            url:'/ubifeed',
            data:{action:'log-out'},
            type:'POST',
            success: function(response){
                
            }
        });
        console.log(timeout)
        clearTimeout(timeout);
        $('#alert').text("");
        $('#connection').show();
        $('#restaurant').hide()
        $('#pickup_station').hide()
    })
    $('.hide_show').on('click', function(){
        hideShowButtonInit($(this))
    })

    function hideShowButtonInit(button) {
        let parentTable = button.closest('table');
        let nextRow = button.parent().parent().index()+1;
        let nextTr = parentTable.find('tr:eq('+nextRow+')');
        
        nextTr.is(':hidden') ? nextTr.show() : nextTr.hide();
    }

    function addInfoCookie(id, role){
        cookie["id"]=parseInt(id);
        cookie["role"]= role;
    }

    function getOrdersRest(){
        $.ajax({
            url:'/ubifeed',
            data:{action:'get-all-orders-rest'},
            type:'POST',
            success: function(response){
                console.log(response);
            }
        });
    }

    function getOrdersStation(){
        $.ajax({
            url:'/ubifeed',
            data:{action:'get-all-orders-pickup'},
            type:'POST',
            success: function(response){
                console.log(response);
            }
        });
    }

    function setRestTable(){
        getOrdersRest();
        timeout = setInterval(getOrdersRest,10000);
    }
    
    function setStationTable(){
        getOrdersStation();
        timeout = setInterval(getOrdersStation,10000);
    }
});