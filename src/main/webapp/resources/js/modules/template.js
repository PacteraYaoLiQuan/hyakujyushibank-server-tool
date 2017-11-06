insightApp
    // =========================================================================
    // LAYOUT
    // =========================================================================
    .directive('changeLayout', function(){
        
        return {
            restrict: 'A',
            scope: {
                changeLayout: '='
            },
            
            link: function(scope, element, attr) {
                //Default State
                if(scope.changeLayout === '1') {
                    element.prop('checked', true);
                }
                
                //Change State
                element.on('change', function(){
                	console.log("scope.changeLayout", scope.changeLayout);
                    if(element.is(':checked')) {
                        localStorage.setItem('ma-layout-status', 1);
                        scope.$apply(function(){
                            scope.changeLayout = '1';
                        })
                    }
                    else {
                        localStorage.setItem('ma-layout-status', 0);
                        scope.$apply(function(){
                            scope.changeLayout = '0';
                        })
                    }
                })
            }
        }
    })



    // =========================================================================
    // MAINMENU COLLAPSE
    // =========================================================================

    .directive('toggleSidebar', function(){

        return {
            restrict: 'A',
            scope: {
                modelLeft: '='
            },
            
            link: function(scope, element, attr) {
                element.on('click', function(){
 
                    if (element.data('target') === 'mainmenu') {
                        if (scope.modelLeft === false) {
                            scope.$apply(function(){
                                scope.modelLeft = true;
                            })
                        }
                        else {
                            scope.$apply(function(){
                                scope.modelLeft = false;
                            })
                        }
                    }
                })
            }
        }
    
    })
    

    
    // =========================================================================
    // SUBMENU TOGGLE
    // =========================================================================

    .directive('toggleSubmenu', function(){

        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                element.click(function(){
                    element.next().slideToggle(200);
                    element.parent().toggleClass('toggled');
                });
            }
        }
    })


    // =========================================================================
    // STOP PROPAGATION
    // =========================================================================
    
    .directive('stopPropagate', function(){
        return {
            restrict: 'C',
            link: function(scope, element) {
                element.on('click', function(event){
                    event.stopPropagation();
                });
            }
        }
    })

    .directive('aPrevent', function(){
        return {
            restrict: 'C',
            link: function(scope, element) {
                element.on('click', function(event){
                    event.preventDefault();
                });
            }
        }
    })
    
    // =========================================================================
    // ALERT WARNING
    // =========================================================================
    .directive('alertWarning', function(){
        return {
            restrict: 'A',
            scope: {
                alertWarning: '='
            },
            link: function(scope, element, attrs) {
                element.click(function(){
                    swal(scope.alertWarning.params, scope.alertWarning.success);
                });
            }
        }
    })
    
    // =========================================================================
    // DATEPICKER CONVERT
    // =========================================================================
    .directive('datepickerConvert', function(){
        return {
            restrict: 'A',
            require: '?ngModel',
            scope: {
                datepickerConvert: '=',
                datepickerConvertFormat: '='
            },
            link: function(scope, element, attrs, ngModelCtrl) {
                function pad(num, n) {
                    return (Array(n).join(0) + num).slice(-n);
                  }
                scope.$watch("datepickerConvert", function(newVal, oldVal) {
                    if(newVal instanceof Date) {
                        var uw = scope.datepickerConvertFormat.replace(/\b\w+\b/g, function(word){
                            if(word === 'yyyy') {
                                return newVal.getFullYear();
                            }
                            if(word === 'MM') {
                                return pad(newVal.getMonth() + 1, 2);
                            }
                            if(word === 'dd') {
                                return pad(newVal.getDate(), 2);
                            }
                        });
                        ngModelCtrl.$setViewValue(uw == 'undefined'? '': uw);
                        ngModelCtrl.$render();
                    }
                });
            }
        }
    })