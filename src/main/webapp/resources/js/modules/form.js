insightApp 

    // =========================================================================
    // INPUT FEILDS MODIFICATION
    // =========================================================================

    //Add blue animated border and remove with condition when focus and blur

    .directive('fgLine', function(){
        return {
            restrict: 'C',
            link: function(scope, element) {
                if($('.fg-line')[0]) {
                    $('body').on('focus', '.form-control', function(){
                        $(this).closest('.fg-line').addClass('fg-toggled');
                    })

                    $('body').on('blur', '.form-control', function(){
                        var p = $(this).closest('.form-group');
                        var i = p.find('.form-control').val();

                        if (p.hasClass('fg-float')) {
                            if (i.length == 0) {
                                $(this).closest('.fg-line').removeClass('fg-toggled');
                            }
                        }
                        else {
                            $(this).closest('.fg-line').removeClass('fg-toggled');
                        }
                    });
                }
    
            }
        }
        
    }).directive('dateTimePicker', function(){
        return {
            restrict: 'C',
            require : '?ngModel',
            link: function(scope, element,attr,controller) {
            	$(element).on("click", function() {
            		$(".bootstrap-datetimepicker-widget").on("mouseover", function() {
            			$(".bootstrap-datetimepicker-widget td.day").on("click", function() {
    		        		setTimeout(function(){
    		        			if (controller != null && controller != undefined) {
    		        				controller.$setViewValue(element[0].value);
        		            		scope.$apply();
    		        			}
    		        			$(element).blur();
    		        		});
    					});
            		});
            	});
            }
        }
        
    }).directive('upload', function(){
        return {
            restrict: 'A',
            scope: {
            	change: "&"
            },
            link: function(scope, element,attr,controller) {
            	$(element).on("change", function(){
            		scope.change($(element));
            	})
            }
        }
        
    })