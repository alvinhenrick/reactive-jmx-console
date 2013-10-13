'use strict'

class VersionFilter
  constructor: (version) ->
    return (text) ->
      text.replace /\%VERSION\%/mg, version

define ['app'], (app) -> app.filter 'interpolate', ['version', VersionFilter]