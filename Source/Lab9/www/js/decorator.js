var MyUser = function(name) {
    this.name = name;
 
    this.say = function() {
        log.add("MyUser: " + this.name);
    };
}
 
var DecoratedUser = function(user, street) {
    this.user = user;
    this.name = user.name;  // ensures interface stays the same
    this.street = street;
     
    this.say = function() {
        log.add("Decorated User: " + this.name + ", " +
                   this.street );
    };
}
 
// logging helper
 
var log = (function() {
    var log = "";
 
    return {
        add: function(msg) { log += msg + "\n"; },
        show: function() { alert(log); log = ""; }
    }
})();