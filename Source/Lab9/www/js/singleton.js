var User = (function () {
  // Instance stores a reference to the Singleton
  var instance;

  function init() {
    // Singleton
    // Private methods and variables
    function privateMethod(){
        console.log( "I am private" );
    }

    var privateVariable = "User";

    return {
      // Public methods
      fullName: function (name, result, sbp, dbp) {
        return privateVariable+": "+name+ " has recorded " + result + " with SBP: " + sbp + " and DBP: " + dbp;
      },      
    };
  };

  return {
    // Get the Singleton instance if one exists
    // or create one if it doesn't
    getInstance: function () {
      if ( !instance ) {
        instance = init();
      }
      return instance;
    }
  };
})();


