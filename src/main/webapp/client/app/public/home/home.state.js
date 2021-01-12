(function () {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    function stateConfig($stateProvider) {
    	/*
        $stateProvider.state('home', {
            parent: 'public',
            url: '/',
            data: {
                pageTitle: 'global.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/public/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'ctrl'
                }
            }
        });
        */
    }
})();
