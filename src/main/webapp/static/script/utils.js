var Utils = {

  prettyPrintTime: function(millis) {
    var d = new Date(0);
    d.setSeconds(Math.ceil(millis / 1000));
    return /* this.padTime( d.getHours() - 1) + ":" + */ this.padTime(d.getMinutes()) + ":" + this.padTime(d.getSeconds());
  },

  padTime: function(s) {
    return (s + "").length < 2 ? "0" + s : s;
  },
  
  getMinutes: function(millis) {
  	var d = new Date(0);
    d.setSeconds(millis / 1000);
    return ((d.getHours() - 1) * 60) + (d.getMinutes());
  },
  
  parseTime: function(time) {
	  if (!time) {
		  return undefined;
	  }
	  var components = time.split(':');
	  // provide base 10 because components might have leading zeroes
	  var h = parseInt(components[0], 10);
	  var m = parseInt(components[1], 10);
	  var s = parseInt(components[2], 10);
	  return s + 60 * (m + 60 * h);
  },
  
  unparseTime: function(millis) {
    var s = Math.floor(millis / 1000);
    var m = Math.floor(s / 60);
    var h = Math.floor(m / 60);
    s %= 60;
    m %= 60;
    return this.padTime(h) + ":" + this.padTime(m) + ":" + this.padTime(s);
  }
};
