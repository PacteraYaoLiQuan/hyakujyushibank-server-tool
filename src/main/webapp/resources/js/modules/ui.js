insightApp

    // =========================================================================
    // MALIHU SCROLL
    // =========================================================================
    
    //On Custom Class
    .directive('cOverflow', ['scrollService', function(scrollService){
        return {
            restrict: 'C',
            link: function(scope, element) {

                if (!$('html').hasClass('ismobile')) {
                    scrollService.malihuScroll(element, 'minimal-dark', 'y');
                }
            }
        }
    }])

    // =========================================================================
    // WAVES
    // =========================================================================

    // For .btn classes
    .directive('btn', function(){
        return {
            restrict: 'C',
            link: function(scope, element) {
                if(element.hasClass('btn-icon') || element.hasClass('btn-float')) {
                    Waves.attach(element, ['waves-circle']);
                }

                else if(element.hasClass('btn-light')) {
                    Waves.attach(element, ['waves-light']);
                }

                else {
                    Waves.attach(element);
                }

                Waves.init();
            }
        }
    })
    .directive('paginationCustomizeButtons', function($q){
        return {
            restrict: 'A',
            controller: function($scope, $element, $attrs) {
                this.listenName = function() {
                    return $attrs.paginationCustomizeButtons;
                }
            },
            link: function(scope, element, attrs) {
                scope.$on(attrs.paginationCustomizeButtons, function(event,data) {
                    var d = scope.$eval(data.runAction);
                    if(data.callbackAction != null) {
                        try{
                            d.then(function(d){
                                scope.$eval(data.callbackAction)
                            }, function(d){
                                console.log("error!");
                            });
                        } catch(e){
                            console.log(e);
                        }
                    }
                });
            }
        }
    })
    .directive('paginationButtons', function($compile){
        return {
            restrict: 'A',
            scope: {
                'paginationButtons': '='
            },
            link: function(scope, element, attrs) {
                var template = angular.element(document.createElement('div'));
                template.attr({
                    'ng-include': 'paginationButtons'
                });
                element.append(template);
                $compile(template)(scope);
            }
        }
    })
    .directive('customizeButtonClick', function(){
        return {
            restrict: 'A',
            scope: {},
            require: ["^paginationCustomizeButtons", "?buttonDisabled"],
            link: function(scope, element, attrs, ctrl) {
                angular.element(element).on("click", function(){
                    var obj = {
                            "runAction": attrs.customizeButtonClick,
                            "callbackAction": null
                    }
                    if(ctrl[1] != null){
                        scope.$apply(function(){
                            ctrl[1].btnDisabledForTrue();
                            obj.callbackAction = ctrl[1].btnDisabledForFalse;
                        });
                    }
                    
                    scope.$emit(ctrl[0].listenName(), obj);
                });
            }
        }
    })
    .directive('buttonDisabled', function(){
        return {
            restrict: 'A',
            controller: function($scope, $element, $attrs) {
                var disabled = $attrs.defaultDisabled == "true" ? true : false;
                $scope[$attrs.buttonDisabled] = disabled;
                
                this.btnDisabledForTrue = function() {
                    $scope[$attrs.buttonDisabled] = true;
                };
                this.btnDisabledForFalse = function() {
                    $scope[$attrs.buttonDisabled] = false;
                };
            },
            link: function(scope, element, attrs) {}
        }
    })
    