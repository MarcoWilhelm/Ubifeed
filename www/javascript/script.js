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
    $('#to_meals').on('click', function(){
        $('#restaurant_orders').hide();
        $('#meals').show();
        getMeals();
    })
    $('#to_orders').on('click', function(){
        $('#restaurant_orders').show();
        $('#meals').hide();
    })
    $('.log_out').on('click', function(){
        $.ajax({
            url:'/ubifeed',
            data:{action:'log-out'},
            type:'POST',
            success: function(response){
                
            }
        });
        clearTimeout(timeout);
        $('#alert').text("");
        $('#connection').show();
        $('#restaurant').hide()
        $('#pickup_station').hide()
    })

    function hideShowButtonInit(button) {
        let parentTable = button.closest('tbody');
        let nextRow = button.parent().parent().index()+1;
        let nextTr = parentTable.find('tr:eq('+nextRow+')');
        
        nextTr.is(':hidden') ? nextTr.show() : nextTr.hide();
    }

    function changeStatus(button) {
        let parentRow = button.closest('tr');
        console.log(parentRow);
        console.log(parentRow[0].id);
        let orderStatus = parentRow.children('.orderStatus');
        console.log(orderStatus);

        switch(orderStatus[0].textContent) {
            case "ORDERED":
                console.log("ORDERED");
            
        }
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
                $("#tbody").empty();
                for(x in response){
                    addTableRestaurant(response[x]);
                }
                $('.hide_show').on('click', function(){
                    hideShowButtonInit($(this))
                })
                $('.statusButton').on('click', function(event) {
                    changeStatus($(this));
                })
            }
        });
    }
    function getOrdersStation(){
        $.ajax({
            url:'/ubifeed',
            data:{action:'get-all-orders-pickup'},
            type:'POST',
            success: function(response){
                $("tbody").empty();
                for(x in response){
                    addTableStation(response[x]);
                }
                $('.hide_show').on('click', function(){
                    hideShowButtonInit($(this))
                })
            }
        });
    }
    function addTableRestaurant(order){
        $('#restaurant_orders tbody').append("<tr id=\"" + order["orderId"] + "\"><td><button class=\"hide_show\">Hide/Show</button></td><td>"+order["orderId"] +"</td><td>"+ 
        order["user"]["firstName"] + " " + order["user"]["lastName"] + "</td><td>"+order["pickupStation"]["name"] + "</td><td class=\"orderStatus\">"+order["orderStatus"]+"</td><td>"+
        "<button class=\"statusButton\" id=\"" + order["orderId"] + "\">Change Status</button>" + "</td></tr>")
        $('#restaurant_orders tbody').append("<tr><td colspan=\"5\" class=\"order_meals\"></td></tr>");
        addMealsOrder(order["meals"], $("#restaurant_orders tbody *:last('.order_meals')"))
    }
    function addTableStation(order){
        $('#station_orders tbody').append("<tr><td><button class=\"hide_show\">Hide/Show</button></td><td>"+order["orderId"] +"</td><td>"+ 
        order["user"]["firstName"] + " " + order["user"]["lastName"] + "</td><td>"+order["restaurant"]["name"] + " : " + 
        order["restaurant"]["address"]+"</td><td>"+order["orderStatus"]+"</td></tr>")
        $('#station_orders tbody').append("<tr><td colspan=\"3\" class=\"order_meals\"></td></tr>");
        console.log(order)
        addMealsOrder(order["meals"], $("#station_orders tbody *:last('.order_meals')"))
    }

    function addMealsOrder(meals, td){
        td.append("<ul>");
        for(x in meals){
            td.append("<li>"+meals[x]["name"]+ " *"+ meals[x]["quantity"]+"</li>");
        }
        td.append("</ul>");
    }

    function setRestTable(){
        getOrdersRest();
        timeout = setInterval(getOrdersRest,30000);
    }
    
    function setStationTable(){
        getOrdersStation();
        timeout = setInterval(getOrdersStation,30000);
    }

    function getMeals(){
        $.ajax({
            url:'/ubifeed',
            data:{action:'get-meals', restaurantId:cookie["id"]},
            type:'POST',
            success: function(response){
                $("#meals_table tbody").empty();
                for(x in response){
                    addMealTable(response[x], $("#meals_table tbody"));
                }
            }
        });
    }
    function addMealTable(meal, tbody){
        tbody.append("<tr><td>"+meal["mealId"]+"</td><td>"+meal["name"]+"</td><td>"+meal["price"]+"</td><td>"+
        meal["categoryId"]+"</td><td>"+meal["pictures"]+"</td></tr>")
    }




});