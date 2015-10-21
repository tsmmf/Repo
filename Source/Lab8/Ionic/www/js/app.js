angular.module('app', ['ionic','ngCordova'])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
  
  .state('hypertension', {
    url: "/hypertension",
    controller: 'Hypertension',
    templateUrl: "hypertension.html"
  })

  .state('splash', {
    url: '/splash',
    templateUrl: "splash.html"
  })
  
   .state('change', {
    url: '/change',
    controller: 'ChangeController',
    templateUrl: "change.html"
  })
  
    // setup an abstract state for the tabs directive
  .state('validate', {
    url: "/validate",
    controller: 'LoginController',
    templateUrl: "validate.html"
  })
  
      // setup an abstract state for the tabs directive
  .state('signup', {
    url: "/signup",
    controller: 'RegisterController',
    templateUrl: "signup.html"
  });
    
    // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/splash');

})

.controller('Hypertension', function($scope, $http, $httpParamSerializerJQLike, $ionicPopup, $cordovaVibration){
    $scope.detectLogic = function(sbp,dbp,name) {
        // Do some computation..
        var sbpValue=parseFloat(sbp);        
        var dbpValue=parseFloat(dbp);
        var nameValue = name.localeCompare("record");
        var flag = parseInt(nameValue);
        
        if (isNaN(sbpValue) || isNaN(dbpValue))
            return{
                
               "classNameForResult": "codered", 
               "results": "Please enter the values correctly." 
            }
        
        if(sbpValue<110 && dbpValue<140){
        
            if (flag == 1){  
                return {
                    "classNameForResult": "codegreen",
                    "results": "Normal : You don't have hypertension."   
                }
            }
            else {
                return{
                    "classNameForResult": "codegreen",
                    "results": "You are doing good."    
                }
            }          
        }             
            
        if(sbpValue>=110 && sbpValue<=139 && dbpValue<90)
            return{
               "classNameForResult": "codeorange",
               "results": "Pre-Hypertension detected"                  
            }
               
        if(sbpValue>=140 && sbpValue<=159 && dbpValue>=90 && dbpValue<=109)
            return{
               "classNameForResult": "codeorange",
               "results": "State 1 Hypertension detected"   
            } 
               
        if(sbpValue>=160 && sbpValue<=179 && dbpValue>=110)
            return{
               "classNameForResult": "codedarkorange",
               "results": "You have State 2 Hypertension detected"                  
            }        
               
        if (sbpValue>=180 || dbpValue>=110)
            return{                      
                "classNameForResult": "codered",
                "results":"Stage 3 Hypertension (severe) detected"  
            }
    }
              
    $scope.detectView = function(style) {
        document.getElementById("result").className = style["classNameForResult"]; 
		document.getElementById("suggestion").className=style["classNameForSuggestion"];
        document.getElementById("result").innerHTML=style["results"];
        
        $cordovaVibration.vibrate(200);
    }
    
     $scope.delete = function() {
  
        $http({
            method: 'DELETE',
            url : 'https://api.mongolab.com/api/1/databases/sampledb/collections/sample/'+ sessionStorage.getItem("userID")+'?apiKey=hr13z2FWkjHZZ2xmn2AWBDeqOm0Y1RfU',
            contentType: "application/json"
        }).success(function(data) {
            console.log(data);
            var alertPopup = $ionicPopup.alert({
                title: 'Account Deleted !',
                okText: 'Register'
            });
            alertPopup.then(function() {            
                window.location.assign("#/splash");
        });
    })
    }
})

.controller('RegisterController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'register';
    $scope.register = function(username, password, email) {
    console.log("inside register function");
    console.log(username);
    console.log(password);
    $http({
        method: 'POST',
        url : 'https://api.mongolab.com/api/1/databases/sampledb/collections/sample?apiKey=hr13z2FWkjHZZ2xmn2AWBDeqOm0Y1RfU',
        data: JSON.stringify({
                    name: username,
                    password: password,
                    email: email
        }),
    contentType: "application/json"
    }).success(function(data) {
        $scope.username ="";
        $scope.password ="";
        $scope.email ="";
        
        var alertPopup = $ionicPopup.alert({
                title: 'User created!'
                
                //template: 'Please check your credentials!'
            });
         alertPopup.then(function() {            
             
             window.location.assign("#/splash");
         });
    })
    }    
})

.controller('LoginController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'login';
    $scope.login = function(name, pass) {
    console.log("inside login function");
    console.log(name);
    console.log(pass);
    $http({
        method: 'GET',
        url : 'https://api.mongolab.com/api/1/databases/sampledb/collections/sample?q={"name": "' + name + '", "password": "' + pass + '"}&apiKey=hr13z2FWkjHZZ2xmn2AWBDeqOm0Y1RfU',
        contentType: "application/json"
    }).success(function(data) {
        console.log(data);
        if(data!=""){
           sessionStorage.setItem("userID", data["0"]._id.$oid);
           //console.log (data["0"]._id.$oid);
           //console.log (data["0"].email);
           window.location.assign("#/hypertension");        
        }
        else {            
            var alertPopup = $ionicPopup.alert({
                title: 'Invalid login !',
                okText: 'Try again'
            });
            alertPopup.then(function() {            
             
                $scope.name ="";
                $scope.pass ="";
            });            
        }
    })
    }    
})

.controller('ChangeController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'changePass';
    $scope.changePass = function(oldpass, newpass) {
  
    $http({
        method: 'PUT',
        url : 'https://api.mongolab.com/api/1/databases/sampledb/collections/sample/'+ sessionStorage.getItem("userID")+'?apiKey=hr13z2FWkjHZZ2xmn2AWBDeqOm0Y1RfU',
        data: JSON.stringify( { "$set" : { "password" : newpass } } ),
        contentType: "application/json"
    }).success(function(data) {
        console.log(data);
        var alertPopup = $ionicPopup.alert({
                title: 'Password change success !',
                okText: 'Login again'
        });
        alertPopup.then(function() {            
             
            window.location.assign("#/splash");
        });
    })
    }    
});
