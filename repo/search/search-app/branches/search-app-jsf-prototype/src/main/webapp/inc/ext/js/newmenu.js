
var is_safari=false;
if (navigator.userAgent.indexOf('Safari') != -1){
	is_safari=true;
}

if (  (navigator.appName != 'Microsoft Internet Explorer') ) {
	if(window.parent.isHomePage){
		parent.document.getElementById("head").style.height="125px";
	}
	else{
		parent.document.getElementById("head").style.height="57px";	
	}
}

if (  (navigator.appName == 'Microsoft Internet Explorer') ) {
	window.attachEvent("onload", fixHeaderWidth);	
}

function fixHeaderWidth(){
	document.getElementById("search").style.width="570px";
}


var is_ie6 = false;
var is_ie = false;
var is_netscape = false;

if (  (navigator.appName == 'Microsoft Internet Explorer') ) {
	is_ie=true;
	parent.document.getElementById("head").style.height="570px";
}

if(((navigator.userAgent).split("Netscape").length)>1){
	is_netscape=true;
}


if (navigator.userAgent.indexOf("MSIE") != -1) {
	if (navigator.userAgent.indexOf("MSIE 6.0") != -1) {
		is_ie6=true;
	}
}


var offtop=53;
var offleft=9; 
var currentclassname = "";
var levelonewidth=120; 
var leveltwowidth=145; 

var menuopen=false;


function activateMenu(nav) {
	if (is_ie) { 
        var navroot = document.getElementById(nav);
        var lis=navroot.getElementsByTagName("LI");  
        for (i=0; i<lis.length; i++) {
        	if(lis[i].lastChild.tagName=="UL"){
				lis[i].onclick=function() {	
					if(top!=self){
						if(parent.document.getElementById('head').allowTransparency==true){
							this.lastChild.style.display="block"; 
							if(is_ie){
								getElementbyClass("willopen");
								for(var i=0; i<ccollect.length; i++){
									if(ccollect[i].lastChild.tagName){
										ccollect[i].lastChild.className="stayopen";
										if (is_ie6) { 
											var kiddies = this.childNodes; 
											if(window.parent.isHomePage){
												document.getElementById("level_1").style.top= this.offsetTop + offtop +65; 
											}
											else{
												document.getElementById("level_1").style.top= this.offsetTop + offtop ;
											}
											document.getElementById("level_1").style.left= this.offsetLeft+2;
											document.getElementById("level_1").style.width= kiddies[2].offsetWidth;
											document.getElementById("level_1").style.height= kiddies[2].offsetHeight; 
										} // end if (is_ie6) {
									}
								}
							} // end if(is_ie){ 
						}	
					}
					if(top==self){
						this.lastChild.style.display="block"; 
						if(is_ie){
							getElementbyClass("willopen");
							for(var i=0; i<ccollect.length; i++){
								if(ccollect[i].lastChild.tagName){
									ccollect[i].lastChild.className="stayopen";
									if (is_ie6) { 
										var kiddies = this.childNodes; 
										if(window.parent.isHomePage){
											document.getElementById("level_1").style.top= this.offsetTop + offtop +65; 
										}
										else{
											document.getElementById("level_1").style.top= this.offsetTop + offtop ;
										}
										document.getElementById("level_1").style.left= this.offsetLeft+2;
										document.getElementById("level_1").style.width= kiddies[2].offsetWidth;
										document.getElementById("level_1").style.height= kiddies[2].offsetHeight; 
									}
								}
							}
						} // end if(is_ie){ 	
					}
				}
				lis[i].onmouseover=function() {	
					if(is_safari){
						
					}
					if(this.lastChild.className=="stayopen"){	
						if(is_ie){
							this.lastChild.style.display="block";
							if(is_ie6){
								if (this.id){ 
									var kiddies = this.childNodes; 
									if(window.parent.isHomePage){
										document.getElementById("level_1").style.top= this.offsetTop + offtop +65;	
									}
									else{
										document.getElementById("level_1").style.top= this.offsetTop + offtop ;	
									}
									document.getElementById("level_1").style.left= this.offsetLeft+2;
									document.getElementById("level_1").style.width= kiddies[2].offsetWidth;
									document.getElementById("level_1").style.height= kiddies[2].offsetHeight; 
								}
								else{
									var kiddies = this.childNodes; 
									var grannies = this.offsetParent; 
									var greatgrannies = grannies.parentNode; 
									var grannieschild = grannies.childNodes; 
									var listitems = kiddies[2].childNodes;
									var listitems2 = kiddies[2].childNodes;
									var foo1 = greatgrannies.childNodes;
									var foo2 = foo1[2].childNodes;
									var lastitem = ((listitems[listitems.length-1]).childNodes)[0];
	
									var divheight=20; 
									var charsPerLine=23;
									if(window.parent.isHomePage){
										document.getElementById("level_2").style.top = this.offsetTop + offtop+2+65;
									}
									else{
										document.getElementById("level_2").style.top = this.offsetTop + offtop+2;
									}
									document.getElementById("level_2").style.left= greatgrannies.offsetLeft + levelonewidth + offleft-6 + 25;
									document.getElementById("level_2").style.width = leveltwowidth;
									var numLines=Math.ceil((((((listitems[listitems.length-1]).childNodes)[0].innerHTML).length)/charsPerLine));
									if(numLines==1){
										document.getElementById("level_2").style.height =  (  findPosY(listitems[listitems.length-1])  - findPosY(listitems[0])   ) + divheight +1;
									}
									else{
										document.getElementById("level_2").style.height =  (  findPosY(listitems[listitems.length-1])  - findPosY(listitems[0])   ) + divheight + ((numLines-1)*14)+2;
									}
								}
							} //if(is_ie6){
						}
				   	}	
				}
				lis[i].onmouseout=function() { 
					if (is_ie) { 
						this.lastChild.style.display="none";
						if (this.id){ 
							document.getElementById("level_1").style.top=0;
							document.getElementById("level_1").style.left=-200;
							document.getElementById("level_1").style.width=1;
							document.getElementById("level_1").style.height=1;	
						}
						else{
							document.getElementById("level_2").style.top=0;
							document.getElementById("level_2").style.left="-200";
							document.getElementById("level_2").style.width=1;
							document.getElementById("level_2").style.height=1;	
						}
				   } // end if (is_ie) 
				}
            }
        }
    } // end if (is_ie)  
	else{
        var navroot = document.getElementById(nav);
        var lis=navroot.getElementsByTagName("LI");
		for (i=0; i<lis.length; i++) {
			if(lis[i].className=="willopen"){
				lis[i].onclick=function() {	
					parent.document.getElementById("head").style.height="570px";	
				   getElementbyClass("willopen");
				   for(var i=0; i<ccollect.length; i++){
					   ccollect[i].className="clickhover";   
				   }
				}
			}
		}
	}
}

function resetmenu(){
	if(is_ie){
		getElementbyClass("stayopen");
		for(var i=0; i<ccollect.length; i++){
			ccollect[i].className="willopen";
		}
	}
	else if (navigator.userAgent.indexOf("Safari") != -1) {
		// done elsewhere....
	}
	else{
		getElementbyClass("clickhover");
		if(window.parent.isHomePage){
			parent.document.getElementById("head").style.height="125px";
		}
		else{
			parent.document.getElementById("head").style.height="57px";	
		}
		for(var i=0; i<ccollect.length; i++){
			ccollect[i].className="willopen";
		}
	}
}

window.onload= function(){
    activateMenu('nav'); 
}

function getElementbyClass(classname){
	ccollect=new Array()
	var inc=0;
	var alltags=document.all? document.all : document.getElementsByTagName("*");
	for (i=0; i<alltags.length; i++){
		if (alltags[i].className==classname) {
			ccollect[inc++]=alltags[i];
		}
	}
}

function findPosX(obj){
var curleft = 0;
if(obj.offsetParent)
	while(1) {
	  curleft += obj.offsetLeft;
	  if(!obj.offsetParent)
		break;
	  obj = obj.offsetParent;
	}
else if(obj.x)
	curleft += obj.x;
return curleft;
}

function findPosY(obj){
var curtop = 0;
if(obj.offsetParent)
	while(1){
	  curtop += obj.offsetTop;
	  if(!obj.offsetParent)
		break;
	  obj = obj.offsetParent;
	}
else if(obj.y)
	curtop += obj.y;
return curtop;
}

function resetmenu_safari(){
	getElementbyClass("clickhover");
	if(window.parent.isHomePage){
		parent.document.getElementById("head").style.height="125px";
	}
	else{
		parent.document.getElementById("head").style.height="57px";	
	}
	for(var i=0; i<ccollect.length; i++){
		ccollect[i].className="willopen";
	}
}



