angular.module('app', ['ionic'])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider
  
  .state('splash', {
    url: '/splash',
    templateUrl: "splash.html"
  })
  
   .state('text', {
    url: '/text',
    controller: 'textController',
    templateUrl: "text.html"
  })
  
  .state('image', {
    url: "/image",
    controller: 'imageController',
    templateUrl: "image.html"
  });
    
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/splash');

})

.controller('imageController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'image';    
    $scope.showImage = function(url){
        $scope.images = [url];
        console.log("inside image recognition function");
        $http({
            method: 'POST',
            url : 'http://localhost:9973/Assignment10/GetImageFeatures',
            data: { "url": url                 
                  },
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function(data) {
            console.log(data);
            var alertPopup = $ionicPopup.alert({
                title: 'Image analysis successful',
                template: "Type of image = "+data.Type,
                okText: 'Check again'
            });
            alertPopup.then(function() {
                $scope.keytext = "";            
                window.location.assign("#/splash");
			});
		})   
    }    
})

.controller('textController', function ($scope, $http, $httpParamSerializerJQLike, $ionicPopup) {

    $scope.pageClass = 'getSentiment';
    $scope.getSentiment = function(keytext) {
    console.log("inside getSentiment function with " + keytext);    
   
     $http({
        method: 'GET',
        url : 'http://localhost:9973/Assignment10/GetTextSentiment?keytext='+keytext,
        contentType: "application/json"
    }).success(function(data) {
        console.log(data);
        console.log(data.Score);
        var alertPopup = $ionicPopup.alert({
                title: "Sentiment Type = "+data.Type,
                template: "Score of sentiment = "+data.Score,
                okText: 'Check again'
        });
        alertPopup.then(function() {            
            $scope.keytext = "";
            window.location.assign("#/splash");
        });
    })
    }    
});
