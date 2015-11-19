angular.module('app', ['ionic'])

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
  
   .state('text', {
    url: '/text',
    controller: 'textController',
    templateUrl: "text.html"
  })
  
    // setup an abstract state for the tabs directive
  .state('image', {
    url: "/image",
    controller: 'imageController',
    templateUrl: "image.html"
  });
    
    // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/splash');

})

.controller('Hypertension', function($scope, $http, $httpParamSerializerJQLike, $ionicPopup){
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
        
            if (flag < 1) {
                
                return {
            
                "classNameForResult": "codegreen",
                "results": "Normal : You don't have hypertension."   
                }
            }
            else if (flag > 0) {
                
                return {
            
                "classNameForResult": "codegreen",
                "results": "Advice : you are doing good."    
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
    }
    
     $scope.delete = function() {
        console.log("inside delete function");
        var email = sessionStorage.getItem("userEmail");
        var name = sessionStorage.getItem("userName");
        $http({
            method: 'POST',
            url : 'http://localhost:9080/FirstMongoProject/DeleteDocument',
            data: { "name": name,
                    "email": email                 
                  },
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
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
         headers: {'Content-Type': 'application/x-www-form-urlencoded'},
         url : "http://localhost:9080/FirstMongoProject/UpdateDocument",
         data: { "name": username,
                 "email": email,
                 "password": password
               }
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

.controller('imageController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'login';
    $scope.login = function(name, pass) {
    console.log("inside login function");  
    $http({
        method: 'GET',
        url : 'http://localhost:9080/FirstMongoProject/GetDocument?name='+name+'&password='+pass,
        contentType: "application/json"
    }).success(function(data) {
        console.log(data);
        if(data!=""){
           sessionStorage.setItem("userEmail", data["0"].email);
           sessionStorage.setItem("userName", data["0"].name);
           console.log (data["0"]._id.$oid);
           console.log (data["0"].email);
           console.log (data["0"].name);
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

.controller('textController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'changePass';
    $scope.changePass = function(oldpass, newpass) {
    console.log("inside change password function");
        
    $http({
         method: 'POST',
         headers: {'Content-Type': 'application/x-www-form-urlencoded'},
         url : "http://localhost:9080/FirstMongoProject/ModifyDocument",
         data: { "email": sessionStorage.getItem("userEmail"),
                 "username": sessionStorage.getItem("userName"),
                 "oldpass": oldpass,
                 "newpass": newpass
               }
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
