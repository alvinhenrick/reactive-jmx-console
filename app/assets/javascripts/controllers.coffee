'use strict'

class MyCtrl1
  constructor: ($log) ->
    $log.info 'MyCtrl1'

class MyCtrl2
  constructor: ($log) ->
    $log.info 'MyCtrl2'

class ExampleCtrl
  constructor:($log,$scope,SocketService) ->
    $scope.exampleData = [
     {
      key: "System Load Average"
#      values: [[1378387200.0, 1.8623046875], [1378387500.0, 1.7023046875], [1378387800.0, 126.92131333333332], [1378388100.0, 122.06958666666667], [1378388400.0, 126.50453], [1378388700.0, 168.14301666666668], [1378389000.0, 132.83243], [1378389300.0, 137.11919333333336], [1378389600.0, 152.85155], [1378389900.0, 133.26816], [1378390200.0, 178.5094466666667], [1378390500.0, 156.0947666666667]]
      values: [[0,0]]
     }
    ]

    $scope.xAxisTickValuesFunction = ->
      (d) ->
        tickVals = []
        values = d[0].values
        interestedTimeValuesArray = [0,10,20,30,40,50]
        for i of values
          tickVals.push values[i][0]  if interestedTimeValuesArray.indexOf(moment.unix(values[i][0]).second()) >= 0
        console.log "xAxisTickValuesFunction", d
        tickVals

    $scope.xAxisTickFormatFunction = ->
      (d) -> d3.time.format("%H:%M:%S") moment.unix(d).toDate()

    $scope.xFunction = ->
      (d) ->
        d[0]

    $scope.yFunction = ->
      (d) ->
        d[1]

    SocketService.connect()

    SocketService.subscribe (message) ->
      return if message == "Succeeded"
      response = angular.fromJson(message)
      $scope.$apply ->
        data = $scope.exampleData[0].values
        data.push([response.data.x,response.data.y])
        data = _.last(data,10)
        $scope.exampleData = [
          {
            key: "System Load Average"
            values: data
          }
        ]
        $log.info $scope.exampleData
        return
      return



define ['app'], (app) -> app.controller('MyCtrl1', ['$log', MyCtrl1])
                            .controller('MyCtrl2', ['$log', MyCtrl2])
                            .controller('ExampleCtrl',['$log','$scope','SocketService',ExampleCtrl])


