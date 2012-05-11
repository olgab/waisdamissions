var randomUUID = function() {
	var s = []
	var itoh = '0123456789ABCDEF'.split(''); ;
	for (var i = 0; i <16; i++) s[i] = itoh[Math.floor(Math.random()*0x10)];
	return s.join('');
}

function UGSLPlayer(elm, src, config) {

    // Create unique element name
    var playerId = "ugsl" + randomUUID();

    // Write Silverlight object tag
    var silverlightObj = ''+
        '<object id="' + playerId + '" data="data:application/x-silverlight-2," type="application/x-silverlight-2" width="620" height="349">' +
        '<param name="source" value="'+src+'"/>'+
        '<param name="windowless" value="true"/>'+
        '<param name="enablehtmlaccess" value="true"/>'+
        '<param name="onload" value="onSilverlightLoad"/>'+
        '<param name="initParams" value="'+
            'embedEnabled=no,playlistEnabled=no,playerId=' + playerId;

            // Add config properties to initParams
            for (var property in config) {
                silverlightObj += ',' + property + '=' + config[property];
            }

    // Finish writing Silverlight object tag
    silverlightObj += '"/><a href="http://go.microsoft.com/fwlink/?LinkID=124807" style="text-decoration: none;">' +
        '<img src="/images/logos/nosilverlight.jpg" alt="Microsoft Silverlight niet gevonden" style="border-style: none"/></a></object>';

    // Build Silverlight object into HTML element
    elm.innerHTML = silverlightObj;

    // Create access methods for config and playlist properties
    document.getElementById(playerId).getConfigProperty = function(property) {
        if (this.content != null)
            return this.content.ugslAccess.getConfigProperty(property);
    };

    document.getElementById(playerId).setConfigProperty = function(property, value) {
        if (this.content != null)
            this.content.ugslAccess.setConfigProperty(property, value);
    };

    document.getElementById(playerId).getPlaylistProperty = function(property, index) {
        if (this.content != null)
            return this.content.ugslAccess.getPlaylistProperty(property, index);
    };

    document.getElementById(playerId).setPlaylistProperty = function(property, index) {
        if (this.content != null)
            this.content.ugslAccess.setPlaylistProperty(property, index);
    };

    // Return silverlight object
    return document.getElementById(playerId);
}
