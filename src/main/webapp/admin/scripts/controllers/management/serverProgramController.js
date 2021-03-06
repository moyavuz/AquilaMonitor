"use strict";
angular.module("sbAdminApp").controller('serverProgramCtrl', ['$scope', 'serverProgramFactory', 'Notification', 'serverFactory', 'programFactory',
    function ($scope, factory, Notification, serverFactory, programFactory) {

        $scope.status;
        $scope.listBean;
        $scope.bean;
        $scope.listProgram;
        $scope.listServer;

        getList();
        getServerList();
        getProgramList();

        function getServerList() {
            console.log('getServerList is working!');
            serverFactory.getList()
                .success(function (list) {
                    $scope.listProgram = list;
                })

                .error(function (error) {
                    $scope.status = 'Unable to load listBean data: ' + error.message;
                    console.log($scope.status);
                });
        }

        function getProgramList() {
            console.log('getProgramList is working!');
            programFactory.getList()
                .success(function (list) {
                    $scope.listServer = list;
                })

                .error(function (error) {
                    $scope.status = 'Unable to load listBean data: ' + error.message;
                    console.log($scope.status);
                });
        }


        function getList() {
            console.log('getList is working!');
            factory.getList()
                .success(function (list) {
                    $scope.listBean = list;
                })

                .error(function (error) {
                    $scope.status = 'Unable to load listBean data: ' + error.message;
                    console.log($scope.status);
                });
        }

        $scope.setBean = function (bean) {
            console.log('setBean is working!');
            factory.tempBean = bean;
            $scope.bean = bean;
        };

        $scope.add = function () {
            console.log('add is working!');
            factory.tempBean = {};
        };

        $scope.delete = function () {
            console.log('delete is working!');
            var selectedBean = $scope.bean;
            factory.deleteBean(selectedBean.id)
                .success(function () {
                    $scope.status = 'Deleted ! Refreshing os list.';

                    for (var i = 0; i < $scope.listBean.length; i++) {
                        var temp = $scope.listBean[i];
                        if (temp.id == selectedBean.id) {
                            $scope.listBean.splice(i, 1);
                            break;
                        }
                    }
                    Notification.success($scope.status);
                    console.log($scope.status);
                })
                .error(function (error) {
                    $scope.status = 'Unable to delete : ' + error.message;
                    Notification.error($scope.status);
                    console.log($scope.status);
                });

        };

    }]);