var app = angular.module('app', ['ui.router', 'jsonFormatter']);

app.config(function($stateProvider, $qProvider){

  $qProvider.errorOnUnhandledRejections(false);

  var accountsState = {
    name: 'accounts',
    url: '/accounts',
    templateUrl: '/accountsTemplate.html',
    controller: 'Controller'
  }

  var paymentsState = {
    name: 'payments',
    url: '/payments',
    templateUrl: '/paymentsTemplate.html',
    controller: 'Controller'
  }

  $stateProvider.state(accountsState);
  $stateProvider.state(paymentsState);

});

app.controller('Controller', function($scope, $http, $state) {
    $scope.transactions = {
        select: 'user to see transactions'
    };
    $http.get('http://localhost:8080/accounts').then(function(res) {
        console.log(res.data);
        $scope.accounts = res.data;
    });

    $scope.viewTransactions = function(id) {
        console.log(id);
        $http.get('http://localhost:8080/transaction/'+id).then(function(res) {
            console.log(res.data);
            $scope.transactions = res.data;
        });
    };

    $scope.pay = function(payee, receiver, money) {
      // $http post was playing up with response.
      fetch('http://localhost:8080/transaction/pay/' + money + '/from/' + payee + '/to/' + receiver, {
        method: 'post'
      }).then(function(response) {
        console.log(response);
        response.text().then(function(data) {
            console.log(data);
            if("successful" === data) {
                $state.go('accounts');
            }else{
                alert('not enough funds sorry');
            }
          });
      }).catch(function(err) {
        console.log(err);
      });

    };


});