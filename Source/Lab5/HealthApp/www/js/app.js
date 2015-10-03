// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter', ['ionic'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if(window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})

.controller('Hypertension', function($scope){
    $scope.diaDetectLogic = function(sbp,dbp) {
        // Do some computation..
        var sbpValue=parseFloat(sbp);
        console.log(sbpValue);
        var dbpValue=parseFloat(dbp);
        console.log(dbpValue);
        if(sbpValue<110 && dbpValue<140){
        return {
            "classNameForResult": "card",
            "classNameForSuggestion": "bar bar-footer bar-balanced",
            "results": "Normal : You don't have hypertension."
        }
        }
        
          if(sbpValue>=120 && sbpValue<=139 && dbpValue<90)
               return{
            "classNameForResult": "card",
            "classNameForSuggestion": "bar bar-footer bar-balanced",
            "results": "You have Pre-Hypertension."                  
    }
               
        if(sbpValue>=140 && sbpValue<=159 && dbpValue>=100)
               return{
            "classNameForResult": "card",
            "classNameForSuggestion": "bar bar-footer bar-balanced",
            "results": "You have State 1 Hypertension."                  
    } 
               
               
        if(sbpValue>=160 && sbpValue<=179 && dbpValue>=110)
               return{
            "classNameForResult": "card",
            "classNameForSuggestion": "bar bar-footer bar-balanced",
            "results": "You have State 2 Hypertension."                  
    }        
               
              if (sbpValue>=180 || dbpValue>=110)
                  return{
                  "classNameForResult": "card",
            "classNameForSuggestion": "bar bar-footer bar-balanced",
            "results":"You have stage 3 Hypertension (severe)."
                  }
                  }
              
    $scope.diaDetectView = function(style) {
        document.getElementById("result").className = style["classNameForResult"];      document.getElementById("suggestion").className=style["classNameForSuggestion"];
        console.log(style["classNameForSuggestion"]);
        document.getElementById("result").innerHTML=style["results"];

    }

        
        
});
