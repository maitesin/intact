
var Biojs=function(){};Biojs.EventHandler=function(eventType){this.eventType=eventType;this.listeners=[];this.addListener=function(actionPerformed){if((typeof actionPerformed)=="function"){this.listeners.push(actionPerformed);}};this.triggerEvent=function(eventObject){for(var i in this.listeners){this.listeners[i](eventObject);}}};Biojs.Event=function(type,data,source){this.source=source;this.type=type;for(var key in data){this[key]=data[key];}};Biojs.Utils={clone:function(obj){var newObj=(obj instanceof Array)?[]:{};for(i in obj){if(obj[i]&&typeof obj[i]=="object"){newObj[i]=Biojs.Utils.clone(obj[i]);}else{newObj[i]=obj[i];}}
return newObj;},isEmpty:function(o){if(o instanceof Array){return(o.length<=0);}else{for(var i in o){if(o.hasOwnProperty(i)){return false;}}
return true;}},console:{enable:function(){if(window.console){this.log=function(msg){console.log(msg)};}else{if(window.opera){this.log=function(msg){window.opera.postError(msg)};}else{var consoleWin=window.open('','myconsole','width=350,height=250'
+',menubar=0'
+',toolbar=0'
+',status=0'
+',scrollbars=1'
+',resizable=1');if(consoleWin){consoleWin.document.writeln('<html><head><title>BioJS Console</title></head>'
+'<body bgcolor=white onLoad="self.focus()">'
+'<div id="Biojs.console"></div>'
+'</body></html>');consoleWin.document.close();Biojs.console.domDocument=consoleWin.document;Biojs.console.domDivNode=consoleWin.document.getElementById("Biojs.console");this.log=function(msg){var message='';if(msg instanceof Array){for(i=0;i<msg.length;i++){message+='['+i+']='+msg[i]+' ';}}else if(msg instanceof String||typeof msg==="string"){message=msg;}else{for(var i in msg){message+='['+i+']='+msg[i]+' ';}}
textNode=Biojs.console.domDocument.createTextNode(message);line=Biojs.console.domDocument.createElement('pre');line.appendChild(textNode);Biojs.console.domDivNode.appendChild(line);};}else{alert("Please activate the pop-up window in this page "+"in order to enable the BioJS console");}}}},log:function(msg){;}}};Biojs.extend=function(_child,_static){var extend=Biojs.prototype.extend;Biojs._prototyping=true;var proto=new this;if(proto.eventTypes instanceof Array){for(var i in proto.eventTypes){_child.eventTypes.push(proto.eventTypes[i]);}}
if(proto.opt instanceof Object){for(var key in proto.opt){_child.opt[key]=proto.opt[key];}}
extend.call(proto,_child);proto.base=function(){};delete Biojs._prototyping;var constructor=proto.constructor;var klass=proto.constructor=function(){if(!Biojs._prototyping){if(this.constructor==klass){function BiojsComponent(){};BiojsComponent.prototype=proto;var instance=new BiojsComponent();instance.setOptions(arguments[0]);instance.setEventHandlers(instance.eventTypes);instance.biojsObjectId=Biojs.uniqueId();constructor.apply(instance,arguments);return instance;}else{constructor.apply(this,arguments);}}};klass.ancestor=this;klass.extend=this.extend;klass.forEach=this.forEach;klass.implement=this.implement;klass.prototype=proto;klass.valueOf=function(type){return(type=="object")?klass:constructor.valueOf();};klass.toString=this.toString;extend.call(klass,_static);if(typeof klass.init=="function"){klass.init();}
return klass;};Biojs.prototype={extend:function(source,value){if(arguments.length>1){var ancestor=this[source];if(ancestor&&(typeof value=="function")&&(!ancestor.valueOf||ancestor.valueOf()!=value.valueOf())&&/\bbase\b/.test(value)){var method=value.valueOf();value=function(){var previous=this.base||Biojs.prototype.base;this.base=ancestor;var returnValue=method.apply(this,arguments);this.base=previous;return returnValue;};value.valueOf=function(type){return(type=="object")?value:method;};value.toString=Biojs.toString;}
this[source]=value;}else if(source){var extend=Biojs.prototype.extend;if(!Biojs._prototyping&&typeof this!="function"){extend=this.extend||extend;}
var proto={toSource:null};var hidden=["constructor","toString","valueOf"];var i=Biojs._prototyping?0:1;while(key=hidden[i++]){if(source[key]!=proto[key]){extend.call(this,key,source[key]);}}
for(var key in source){if(!proto[key])extend.call(this,key,source[key]);}}
return this;},addListener:function(eventType,actionPerformed){if(this._eventHandlers){for(var key in this._eventHandlers){if(eventType==this._eventHandlers[key].eventType){this._eventHandlers[key].addListener(actionPerformed);return;}}}},setEventHandlers:function(eventTypes){this._eventHandlers=[];var alias=function(handler){return function(actionPerformed){handler.listeners.push(actionPerformed);}};if(typeof eventTypes=="object"){for(var i=0;i<eventTypes.length;i++){var handler=new Biojs.EventHandler(eventTypes[i]);this._eventHandlers.push(handler);this[eventTypes[i]]=new alias(handler);}}},raiseEvent:function(eventType,eventObj){for(var key in this._eventHandlers){if(eventType==this._eventHandlers[key].eventType){this._eventHandlers[key].triggerEvent(eventObj);return;}}},setOptions:function(options){if(this.opt instanceof Object)
{this.opt=Biojs.Utils.clone(this.opt);for(var key in options){this.opt[key]=options[key];}}},listen:function(source,eventType,callbackFunction){if(source instanceof Biojs){if(typeof callbackFunction=="function"){source.addListener(eventType,callbackFunction);}}},getId:function(){return this.biojsObjectId;}};Biojs=Biojs.extend({constructor:function(){this.extend(arguments[0]);},vaueOf:function(){return"Biojs"}},{ancestor:Object,version:"1.0",forEach:function(object,block,context){for(var key in object){if(this.prototype[key]===undefined){block.call(context,object[key],key,object);}}},implement:function(){for(var i=0;i<arguments.length;i++){if(typeof arguments[i]=="function"){arguments[i](this.prototype);}else{this.prototype.extend(arguments[i]);}}
return this;},toString:function(){return String(this.valueOf());},uniqueId:function(){if(typeof Biojs.prototype.__uniqueid=="undefined"){Biojs.prototype.__uniqueid=0;}
return Biojs.prototype.__uniqueid++;},registerGlobal:function(key,value){window[key]=value;},getGlobal:function(key){return window[key];},console:Biojs.Utils.console,EventHandler:Biojs.EventHandler,Event:Biojs.Event,Utils:Biojs.Utils});