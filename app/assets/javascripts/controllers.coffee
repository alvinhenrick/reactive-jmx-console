/*global define*/
'use strict'

class MyCtrl1
  constructor: ($log) ->
    $log.info 'MyCtrl1'

class MyCtrl2
  constructor: ($log) ->
    $log.info 'MyCtrl2'

define ['app'], (app) -> app.controller('MyCtrl1', ['$log', MyCtrl1])
                            .controller('MyCtrl2', ['$log', MyCtrl2])

