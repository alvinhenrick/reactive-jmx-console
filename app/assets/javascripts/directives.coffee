'use strict'

class VersionDirective
  constructor: (version) ->
    return (scope, element, attrs) ->
      element.text(version)
    return

define ['app'], (app) ->
  app.directive 'appVersion', ['version', VersionDirective]
