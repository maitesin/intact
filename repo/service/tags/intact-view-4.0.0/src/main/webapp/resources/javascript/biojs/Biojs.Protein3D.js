
Biojs.Protein3D=Biojs.extend({constructor:function(options){Biojs.console.log("starting Biojs.Protein3D constructor");var self=this;self.image='<img id="image_'+self.getId()+'" src="'+self.opt.loadingStatusImage+'" style="display:block; margin: 0 auto;" />';self.opt.backgroundColor=(self.opt.backgroundColor)?self.opt.backgroundColor:"white";jmolInitialize(self.opt.jmolFolder);jmolSetAppletColor(self.opt.backgroundColor);jmolSetDocument(0);Biojs.console.log("registring callback for object "+self.getId());self._appletId="jmolApplet"+self.getId();var loadStructCallbackName=self._appletId+"_pdbLoadCallback";Biojs.registerGlobal(self._appletId,self);Biojs.registerGlobal(loadStructCallbackName,function(appletId,url,file,title,message,code,formerFrame,frame){if(Biojs.getGlobal(appletId).opt.enableControls){Biojs.getGlobal(appletId).showControls();}
if(file!==null){window[appletId].raiseEvent("onPdbLoaded",{file:title,result:(code==3)?'success':'failure',message:message});}});jmolSetCallback("loadStructCallback",loadStructCallbackName);$("div#"+self.opt.target).css('display','block').css("padding","0px").css("width",self.opt.width).css("height",self.opt.height).css("overflow","hidden").html('<div id="div'+self._appletId+'"/><div id="controlSection" />').find('div').css('position','relative').css('float','right').hide();$("div#"+self.opt.target).append('<div id="loadingImage" />');$('#'+self.opt.target+' div#loadingImage').css("padding","0px").css("width",self.opt.width).css("height",self.opt.height).css("overflow","hidden").css('display','table-cell').css('vertical-align','middle').html(self.image);if($.browser.msie){$('#'+self.opt.target+' div#loadingImage').css('display','inline');$('#'+self.opt.target+' img#image_'+this.getId()).css('vertical-align','middle');}
Biojs.console.log("ending Biojs.Protein3D constructor");},opt:{target:'component',width:597,height:400,jmolFolder:'../biojs/dependencies/jmol-12.0.48',unpolarColor:"salmon",negativeColor:"red",positiveColor:"blue",polarColor:"yellow",backgroundColor:"white",enableControls:true,loadingStatusImage:'../biojs/images/ajax-loader-1.gif'},eventTypes:["onPdbLoaded","onSelection"],_appletId:undefined,_controlsReady:false,_controlsVisible:false,_jmoljarfile:"JmolApplet.jar",_jmolAppletInitialized:false,_selection:undefined,_display:{property:{polar:false,unpolar:false,positive:false,negative:false},surface:false,halos:true},showControls:function(){if(this.opt.enableControls){if(!this._controlsReady){this._buildControls();}
if(!this._controlsVisible){this._toggleControls();}}},hideControls:function(){if(this.opt.enableControls&&this._controlsVisible){this._toggleControls();}},getSelection:function(){return Biojs.Utils.clone(this._selection);},reset:function(){jmolScriptWait(this._getDisplayColor("By Chain")+this._getDisplayStyle("Cartoon"));var theTargetDiv=$("#"+this.opt.target);theTargetDiv.find("div#controlSection > div#controls > input[type='checkbox']").attr("checked",false);theTargetDiv.find('#styleSelect').val("Cartoon");theTargetDiv.find('#colorSelect').val("By Chain");theTargetDiv.find('#surfaceSelect').val("None");for(var key in this._display.property){this._display.property[key]=false;}
this.setHalosVisible(true);},showLoadingImage:function(){$('#'+this.opt.target).find('div').hide();$("#"+this.opt.target+' div#loadingImage').show();},setPdb:function(pdb){Biojs.console.log("LOADING pdb:");Biojs.console.log(pdb);var self=this;var scr="";scr+=this._getDisplayColor("By Chain")+this._getDisplayStyle("Cartoon")+this._getDisplaySurface("None");scr+=this._getSelectionScript(this._selection);$('#'+self.opt.target).find('div').show();$("#"+self.opt.target+' div#loadingImage').hide();if(this._jmolAppletInitialized){this.reset();}
this.jmolHTML=jmolAppletInline([this.opt.width,this.opt.height],pdb,scr,this.getId());$("div#"+self.opt.target+' #div'+self._appletId).html(self.jmolHTML);this.hideControls();this.showControls();this._jmolAppletInitialized=true;Biojs.console.log("setPdb() ending");},removeSelection:function(){this._selection=undefined;var scr='select all; ';scr+=(!this._display.halos)?'color translucent 1; ':'selectionHalos off; ';scr+='select none;'
jmolScriptWait(scr);},setSelection:function(selection){if(selection instanceof Array){this._selection=Biojs.Utils.clone(selection);this._drawSelection();this.raiseEvent("onSelection",{selectionType:"positions",selection:Biojs.Utils.clone(selection)});}
else if(selection instanceof Object&&selection.start<=selection.end){this._selection=Biojs.Utils.clone(selection);this._drawSelection();this.raiseEvent("onSelection",{selectionType:"region",selection:Biojs.Utils.clone(selection)});}else{Biojs.console.log("selection not valid");}},_getSelectionScript:function(selection){var scr="";var selectionText="";if(selection){scr='select all; color translucent 1;';selectionText=(selection instanceof Array)?selection.join(","):selection.start+"-"+selection.end;if(!this._display.halos){scr+='select not '+selectionText+'; color translucent 0.8; selectionHalos off;';}else{scr+='select '+selectionText+'; selectionHalos on;';}}else{scr="select none";}
return scr;},_drawSelection:function(){if(this._selection!==undefined){result=jmolScriptWait(this._getSelectionScript(this._selection));Biojs.console.log("Selection done, result: "+result);}},displaySurface:function(name){var surface=this._getDisplaySurface(name);if(surface==undefined){name="None";}
$("#"+this.opt.target).find('#surfaceSelect').val(name);jmolScriptWait(surface+this._getSelectionScript(this._selection));},hideSurface:function(){var surfaceSelect=$("#"+this.opt.target).find('#surfaceSelect');if(surfaceSelect.val(name)!="None"){surfaceSelect.val("None");jmolScriptWait(this._getDisplaySurface("None")+this._getSelectionScript(this._selection));}},displayNegative:function(color){$("#"+this.opt.target).find('#negativeCheck').attr("checked","negativeCheck");negativeColor=(color)?color:this.opt.negativeColor;this.display('acidic',negativeColor);this._display.property.negative=true;},hideNegative:function(){if(this._display.property.negative){$("#"+this.opt.target).find('#negativeCheck').removeAttr("checked");this._display.property.negative=false;this.undisplay('acidic');}},displayPositive:function(color){positiveColor=(color)?color:this.opt.positiveColor;$("#"+this.opt.target).find('#positiveCheck').attr("checked","positiveCheck");this.display('basic',positiveColor);this._display.property.positive=true;},hidePositive:function(){if(this._display.property.positive){$("#"+this.opt.target).find('#positiveCheck').removeAttr("checked");this._display.property.positive=false;this.undisplay('basic');}},displayPolar:function(color){$("#"+this.opt.target).find('#polarCheck').attr("checked","polarCheck");polarColor=(color)?color:this.opt.polarColor;this.display('polar',polarColor);this._display.property.polar=true;},hidePolar:function(){if(this._display.property.polar){$("#"+this.opt.target).find('#polarCheck').removeAttr("checked");this._display.property.polar=false;this.undisplay('polar');}},displayUnPolar:function(color){unPolarColor=(color)?color:this.opt.unpolarColor;$("#"+this.opt.target).find('#unpolarCheck').attr("checked","unpolarCheck");this.display('hydrophobic',unPolarColor);this._display.property.unpolar=true;},hideUnPolar:function(){if(this._display.property.unpolar){$("#"+this.opt.target).find('#unpolarCheck').removeAttr("checked");this._display.property.unpolar=false;this.undisplay('hydrophobic');}},setHalosVisible:function(value){this._display.halos=value;if(value){if(!$("#"+this.opt.target).find('#halosRadio:checked').val()){$("#"+this.opt.target).find('#halosRadio').attr("checked","halosRadio");$("#"+this.opt.target).find('#translucentRadio').removeAttr("checked");}}else{if($("#"+this.opt.target).find('#halosRadio:checked').val()){$("#"+this.opt.target).find('#halosRadio').removeAttr("checked");$("#"+this.opt.target).find('#translucentRadio').attr("checked","translucentRadio");}}
this._drawSelection();},applyJmolCommand:function(cmd){jmolScriptWait(cmd);},display:function(property,color){scr=(this._isAnyDisplayed())?'':'select all; color lightgrey;';scr+='select '+property+';color '+color+'; select none';jmolScriptWait(scr);Biojs.console.log("applied: "+scr);this._drawSelection();},undisplay:function(property){scr='select '+property+';color lightgrey; select none;'
scr+=(this._isAnyDisplayed())?'':'select all; color chain; select none;';jmolScriptWait(scr);Biojs.console.log("applied: "+scr);this._drawSelection();},_isAnyDisplayed:function(){for(var key in this._display.property){if(this._display.property[key]){return true;}}
return false;},_buildControls:function(){if(this._controlsReady){return;}
this._controlsVisible=false;Biojs.console.log("_buildControls()");var self=this;var width=Math.round(self.opt.width*0.3);var height=self.opt.height;var controlSectionDiv=$("#"+self.opt.target+" > div#controlSection");controlSectionDiv.append('<div id="controls" />'+'<div id="controlTab">'+'<span id="hideButton" title="Hide this control panel">&lt;&lt;</span>'+'<span id="showButton" title="Show the control panel">&gt;&gt;</span><br/><br/></div>').css("border",0).css("margin",0).css("width","auto").css("height","auto").css('float','left').css('font-family','"Heveltica Neue", Arial, "sans serif"').css('font-size','12px');var padding=3;var tabWidth=20,tabHeight=height;var controlsWidth=width-(2*padding+tabWidth),controlsHeight=height-(padding*2);var controlDiv=controlSectionDiv.find("div#controls");controlDiv.css("position","relative").css("border",0).css("margin",0).css('background-color',"#000").css('color',"#fff").css('float','left').css("width",controlsWidth).css("height",controlsHeight).css("padding",padding).css("text-align","left").append('<b> Display: </b><br/>'+'<input id="polarCheck" type="checkbox" name="polarCheck" value="polarCheck"/> Hydrophylic residues<br/>'+'<input id="unpolarCheck" type="checkbox" name="unpolarCheck" value="unpolarCheck"/> Hydrophobic residues<br/>'+'<input id="positiveCheck" type="checkbox" name="positiveCheck" value="positiveCheck"/> Basic(+) residues<br/>'+'<input id="negativeCheck" type="checkbox" name="negativeCheck" value="negativeCheck"/> Acidic(-) residues<br/>'+'<input id="antialiasCheck" type="checkbox" name="antialiasCheck" value="antialiasCheck"/> Antialias<br/>'+'<input id="backgroundCheck" type="checkbox" name="backgroundCheck" value="backgroundCheck"/> Black background<br/>'+'<input id="rotationCheck" type="checkbox" name="rotationCheck" value="rotationCheck"/> Rotation<br/><br/>'+'Style: <select id="styleSelect">'+'<option selected="selected">Cartoon</option>'+'<option >Backbone</option>'+'<option >CPK</option>'+'<option >Ball and Stick</option>'+'<option >Ligands</option>'+'<option >Ligands and Pocket</option>'+'</select><br/><br/>'+'Color: <select id="colorSelect">'+'<option selected="selected">By Chain</option>'+'<option >Secondary Structure</option>'+'<option >Rainbow</option>'+'<option >By Element</option>'+'<option >By Amino Acid</option>'+'<option >By Temperature</option>'+'<option >Hydrophobicity</option>'+'</select><br/><br/>'+'Surface: <select  id="surfaceSelect">'+'<option selected="selected">None</option>'+'<option >Solvent Accessible</option>'+'<option >Solvent Excluded</option>'+'<option >Cavities</option>'+'</select><br/>'+'<br/><b> Show selection using: </b><br/>'+'<input id="translucentRadio" type="radio" name="selection" value="translucentRadio"/> Translucent '+'<input id="halosRadio" type="radio" name="selection" value="halosRadio" checked="halosRadio"/> Halos<br/><br/>');controlDiv.find('#polarCheck').click(function(){if($('#polarCheck:checked').val()){self.displayPolar();}else{self.hidePolar();}});controlDiv.find('#unpolarCheck').click(function(){if($('#unpolarCheck:checked').val()){self.displayUnPolar();}else{self.hideUnPolar();}});controlDiv.find('#positiveCheck').click(function(){if($('#positiveCheck:checked').val()){self.displayPositive();}else{self.hidePositive();}});controlDiv.find('#negativeCheck').click(function(){if($('#negativeCheck:checked').val()){self.displayNegative();}else{self.hideNegative();}});controlDiv.find('#antialiasCheck').click(function(event){self.applyJmolCommand('set antialiasDisplay '+(($('#antialiasCheck:checked').val())?"true":"false"));});controlDiv.find('#rotationCheck').click(function(event){self.applyJmolCommand('spin '+(($('#rotationCheck:checked').val())?"true":"false"));});controlDiv.find('#backgroundCheck').click(function(event){self.applyJmolCommand('background '+(($('#backgroundCheck:checked').val())?"black":self.opt.backgroundColor));});controlDiv.find('#styleSelect').change(function(){self.applyJmolCommand(self._getDisplayStyle($(this).val())+self._getSelectionScript(self._selection));});controlDiv.find('#colorSelect').change(function(){self.applyJmolCommand(self._getDisplayColor($(this).val())+self._getSelectionScript(self._selection));});controlDiv.find('#surfaceSelect').change(function(){self.applyJmolCommand(self._getDisplaySurface($(this).val())+self._getSelectionScript(self._selection));});controlDiv.find('input[name="selection"]').change(function(){var showHalos=($('#halosRadio:checked').val())?true:false;self.setHalosVisible(showHalos);});var showHideTab=controlSectionDiv.find('#controlTab');showHideTab.css("border",0).css("margin",0).css("padding",0).css("float","left").css("width",tabWidth).css("height",tabHeight).css('background-color',"#000");var toggleControls=function(){showHideTab.find("span").toggle();if(self._controlsVisible){var appletWidth=self.opt.width-tabWidth;jmolResizeApplet([appletWidth,self.opt.height],self.getId());$('#div'+self._appletId).css("width",appletWidth);controlDiv.hide();showHideTab.css('background-color',self.opt.backgroundColor);self._controlsVisible=false;}else{var appletWidth=self.opt.width-width;jmolResizeApplet([appletWidth,self.opt.height],self.getId());$('#div'+self._appletId).css("width",appletWidth);controlDiv.show();showHideTab.css('background-color',"#000");self._controlsVisible=true;}}
self._toggleControls=toggleControls;showHideTab.find('#hideButton').click(self._toggleControls).mouseover(function(){$(this).css("color","#27C0FF");}).mouseout(function(){$(this).css("color","#fff");}).css("color","#fff").css("display","none").css("cursor","pointer");showHideTab.find('#showButton').click(self._toggleControls).mouseover(function(){$(this).css("color","#27C0FF");}).mouseout(function(){$(this).css("color","#000");}).css("color","#000").css("cursor","pointer");this._controlsReady=true;Biojs.console.log("_buildControls done");},_addControl:function(html){$('#'+this.opt.target+' div#controls').append(html);},_getDisplayStyle:function(text){if(text=='Cartoon'){return"hide null; select all;  spacefill off; wireframe off; backbone off;"+" cartoon on; "+" select ligand; wireframe 0.16;spacefill 0.5; color cpk; "+" select *.FE; spacefill 0.7; color cpk ; "+" select *.CU; spacefill 0.7; color cpk ; "+" select *.ZN; spacefill 0.7; color cpk ; "+" select all; ";}
else if(text=='Backbone'){return"hide null; select all; spacefill off; wireframe off; backbone 0.4;"+" cartoon off; "+" select ligand; wireframe 0.16;spacefill 0.5; color cpk; "+" select *.FE; spacefill 0.7; color cpk ; "+" select *.CU; spacefill 0.7; color cpk ; "+" select *.ZN; spacefill 0.7; color cpk ; "+" select all; ";}else if(text=='CPK'){return"hide null; select all; spacefill off; wireframe off; backbone off;"+" cartoon off; cpk on;"+" select ligand; wireframe 0.16;spacefill 0.5; color cpk; "+" select *.FE; spacefill 0.7; color cpk ; "+" select *.CU; spacefill 0.7; color cpk ; "+" select *.ZN; spacefill 0.7; color cpk ; "+" select all; ";}
else if(text=='Ligands'){return"restrict ligand; cartoon off; wireframe on;  display selected;"+" select *.FE; spacefill 0.7; color cpk ; "+" select *.CU; spacefill 0.7; color cpk ; "+" select *.ZN; spacefill 0.7; color cpk ; "+" select all; ";}
else if(text=='Ligands and Pocket'){return" select within (6.0,true, ligand; cartoon off; wireframe on; backbone off; display selected; "+" select *.FE; spacefill 0.7; color cpk ; "+" select *.CU; spacefill 0.7; color cpk ; "+" select *.ZN; spacefill 0.7; color cpk ; "+" select all; ";}
else if(text=='Ball and Stick'){return"hide null; restrict not water;  wireframe 0.2; spacefill 25%;"+" cartoon off; backbone off; "+" select ligand; wireframe 0.16; spacefill 0.5; color cpk; "+" select *.FE; spacefill 0.7; color cpk ; "+" select *.CU; spacefill 0.7; color cpk ; "+" select *.ZN; spacefill 0.7; color cpk ; "+" select all; ";}else{return undefined;}},_getDisplayColor:function(text){if(text=='By Chain'){return"hide null; select all; cartoon on; wireframe off; spacefill off; color chain; select ligand; wireframe 0.16; spacefill 0.5; color chain ; select all; ";}
else if(text=='By Temperature'){return"hide null; select all;spacefill off; wireframe off; backbone 0.4; cartoon off; set defaultColors Jmol; color relativeTemperature; color cartoon relateiveTemperature select ligand;wireframe 0.16;spacefill 0.5; color cpk ;; select all; ";}
else if(text=='Rainbow'){return"hide null; select all; set defaultColors Jmol; color group; color cartoon group; select ligand;wireframe 0.16;spacefill 0.5; color cpk ; select all; ";}
else if(text=='Secondary Structure'){return"hide null; select all; set defaultColors Jmol; color structure; color cartoon structure;select ligand;wireframe 0.16;spacefill 0.5; color cpk ;; select all; ";}
else if(text=='By Element'){return"hide null; select all; set defaultColors Jmol; color cpk; color cartoon cpk; select ligand;wireframe 0.16; spacefill 0.5; color cpk ; select all; ";}
else if(text=='By Amino Acid'){return"hide null; select all; set defaultColors Jmol; color amino; color cartoon amino; select ligand;wireframe 0.16;spacefill 0.5; color cpk ;; select all; ";}
else if(text=='Hydrophobicity'){return"hide null; set defaultColors Jmol; select hydrophobic; color red; color cartoon red; select not hydrophobic ; color blue ; color cartoon blue; select ligand;wireframe 0.16;spacefill 0.5; color cpk ;; select all; ";}else{return undefined;}},_getDisplaySurface:function(text){if(text=='None'){return"select all; isosurface off; select none;";}
else if(text=='Solvent Accessible'){return"select all; isosurface sasurface 1.2; color isoSurface translucent 0.8; select none;";}
else if(text=='Solvent Excluded'){return"select all; isosurface solvent 1.2; color isoSurface translucent 0.8; select none;";}
else if(text=='Cavities'){return"select all; isosurface cavity 1.2 10; color isoSurface translucent 0.8; select none;";}else{return undefined;}},toString:function(){return"Biojs.Protein3D";}});