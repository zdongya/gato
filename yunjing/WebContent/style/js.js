<SCRIPT language=javascript>
<!--
	var imgUrl=new Array();
	var imgLink=new Array();
	var label = new Array(); 

	imgUrl[1]="images/01.jpg";
	imgLink[1]="http://www.lanrentuku.com/";
	label[1] = "标题一";

	imgUrl[2]="images/02.jpg";
	imgLink[2]="http://www.lanrentuku.com/";
	label[2] = "标题二";

	imgUrl[3]="images/03.jpg";
	imgLink[3]="http://www.lanrentuku.com/";
	label[3] = "标题三";

	imgUrl[4]="images/04.jpg";
	imgLink[4]="http://www.lanrentuku.com/";
	label[4] = "标题四";

	imgUrl[5]="images/05.jpg";
	imgLink[5]="http://www.lanrentuku.com/";
	label[5] = "标题五";

          
    var num_pic =5;
	var label_width = 380/num_pic-2;
	
	var focusPicNumSrc="http://www.lanrentuku.com/";
	var time1 = 0; //打开页面时等待图片载入的时间，单位为秒，可以设置为0
	var time2 = 4; //图片轮转的间隔时间
	
	var timeout1 = time1*1000;
	var timeout2 = time2*1000;
	var jumpUrl = '';
	var nn=1;//初始焦点
	var curFileNum = 1;//传递给myPlayer对象 表示目前焦点序列值
	
	document.write('<style>');
	document.write('.focusPic {border:1px #333333 solid; OVERFLOW: hidden;  WIDTH: 378px; POSITION: relative; HEIGHT: 213px}');
	document.write('.focusPicNum {Z-INDEX: 99; right: 0px; POSITION: absolute; TOP: 190px;MARGIN: 3px}');
	document.write('</style>');

	if(navigator.appName == "Microsoft Internet Explorer"){
		setTimeout('change_img()',timeout1);
	}
	function change_img(){
		if(nn>num_pic) nn=1;
		setTimeout('setFocus2('+nn+')',timeout1);
		nn++;
		tt=setTimeout('change_img()',timeout2);
	}
	function setFocus2(i){
		jumpUrl=imgLink[i];
		curFileNum = i;
		selectLayer1(i);
		imgInit.filters.revealTrans.Transition=23;
		imgInit.filters.revealTrans.apply();
    	playTran();
		document.images.imgInit.src=imgUrl[i];
	}
	function setFocus1(i){
		nn = i;
		ln=i;
		curFileNum = i;
		selectLayer1(i);
		setFocus2(i);
	}
	function selectLayer1(i){
		for (a=1;a<num_pic+1;a++ ){
			//document.images['fi_'+a].src=focusPicNumSrc+'/bn_focus_num_ws_0'+a+'off.gif';
			//document.getElementById('label_'+id).class='sty20';
			var obj = GetObj('label_'+a);
			obj.className='sty20';
			obj.style.width=label_width;
		}
		//document.images['fi_'+i].src=focusPicNumSrc+'/bn_focus_num_ws_0'+i+'on.gif';
	//	document.getElementById('label_'+id).class='sty21';
		var obj = GetObj('label_'+i);
		obj.className='sty21';
		obj.style.width=label_width;
	}
	function goUrl(){
		ln=nn;
		if (ln ==1)if (jumpUrl!='') jumpUrl=imgLink[ln];
		jumpTarget='_blank';
		if (jumpUrl != ''){
			if (jumpTarget != '')window.open(jumpUrl,jumpTarget);
			else location.href=jumpUrl;
		}
	}

	function playTran(){
		if (document.all)imgInit.filters.revealTrans.play();
	}
	function GetObj(objName){ 
    	if(document.getElementById){ 
    	    return eval('document.getElementById("' + objName + '")'); 
    	}else if(document.layers){ 
    	    return eval("document.layers['" + objName +"']"); 
    	}else{ 
    	    return eval('document.all.' + objName); 
    	} 
	} 
//-->
</SCRIPT>

<DIV class=focusPic id=focusPic>
<SCRIPT language=JavaScript>
<!--
	document.write('<DIV class=focusPicNum style=display:none>');
	for (i=1;i<num_pic+1;i++ )
	{
		document.write('<A href=javascript:setFocus1('+i+');><IMG height=15 src='+focusPicNumSrc+'/bn_focus_num_ws_0'+i+'off.gif width=23 border=0 name=fi_'+i+'></A>');
	}
	document.write('</DIV>');
	document.write('<div id="page_left_1">');
	document.write('<a id="PicLink" href="javascript:goUrl()"><img src="'+imgUrl[1]+'" width=378  name=imgInit height="213" border="0" style="FILTER: revealTrans(duration=2,transition=6)"></a>');
	document.write('</div>');
	
	document.images.imgInit.src=imgUrl[1];

//-->
</SCRIPT>
</DIV>
<DIV class=lanrentuku>
  <SCRIPT language=javascript>
<!--
	for(i=1;i<num_pic+1;i++)
		document.write('<div class="sty20" style="width:'+label_width+'" id="label_'+i+'" onMouseOver="setFocus1('+i+')"><div id="page_left_2_1_1">'+label[i]+'</div></div>');
-->
</SCRIPT>