insightApp
//=========================================================================
// Malihu Scroll - Custom Scroll bars
// =========================================================================
    .service('scrollService', function() {
        var ss = {};
        ss.malihuScroll = function scrollBar(selector, theme, mousewheelaxis) {
            $(selector).mCustomScrollbar({
                theme: theme,
                scrollInertia: 100,
                axis:'yx',
                mouseWheel: {
                    enable: true,
                    axis: mousewheelaxis,
                    preventDefault: true
                }
            });
        }
        
        return ss;
    })
    .service("strSpliceService", function(){
    	return {
    	    resultCodeSplice : function(object, listIndex) {
    	    	
    	    	var returnCodeList = [];
    	    	for (var i = 0; i < object.list.length; i++) {
	    			returnCodeList[i] = object.list[i][listIndex];
    	    	}
    	    	return returnCodeList;
    	    }
    	}
    })