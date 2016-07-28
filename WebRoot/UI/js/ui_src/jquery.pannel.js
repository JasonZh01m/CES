(function($) {

	$.fn.pannel = function(options) {
		if ($.isJson(options)) {
			var o = $(this);
			createHtml(o, options);
			if (options.content && options.content.tbr) {
				initToolbar(options);
			}
		}
	};

	var createHtml = function(o, opt) {
		var divo = o.get(0);
		var pw = opt.width;
		var ph = opt.height;
		o.addClass("pannel");
		o.width(pw + "px");
		o.height(ph + "px");
		var ctw = pw;
		var cth = ph;
		_content = opt.content;
		if (opt.title) {
			var po = $.newEl("p");
			$.addCls(po, "title");
			var spano = $.newEl("span");
			var texto = $.newTn(opt.title);
			spano.appendChild(texto);
			po.appendChild(spano);
			divo.appendChild(po);
			if (_content) {
				cth = ph - 32;
			}
		}
		if (_content) {
			var contento = $.newEl("div");
			contento.style.width = ctw + "px";
			contento.style.height = cth + "px";
			$.addCls(contento, "content");

			var _tbr = _content.tbr;
			var dw = ctw;
			var dh = cth;
			if (_tbr) {
				var tbro = createToolbar(_content.id, _tbr, dw);
				contento.appendChild(tbro);
				dh -= 32;
			}
			var pdiv = createPDiv(_content.id, dw, dh);
			contento.appendChild(pdiv);
			divo.appendChild(contento);
		}

	};

	var createPDiv = function(id, dw, dh) {
		var pdivo = $.newEl("div");
		$.addCls(pdivo, "pdiv", id);
		pdivo.style.width = dw + "px";
		pdivo.style.height = dh + "px";
		return pdivo;
	};
	var createToolbar = function(id, tbr, dw) {
		var tbro = $.newEl("div");
		var _tid = tbr.id;
		if (!_tid) {
			_tid = _content + "_tbr";
		}
		$.addCls(tbro, "tbr", _tid);
		tbro.style.width = dw + "px";
		return tbro;
	};

	var initToolbar = function(options) {
		var _tbr = options.content.tbr;
		if (_tbr) {
			var t_id = _tbr.id;
			if (!t_id) {
				t_id = options.content.id + "_tbr";
			}
			$("#" + t_id).toolbar(_tbr);
		}

	};

})(jQuery);

(function($) {

	var valiReg = /^0.[0-9]*$/;

	$.fn.layout = function(options) {
		if ($.isJson(options)) {
			var o = $(this);
			initLayout(o, options);
		}
	};

	var initLayout = function(o, options) {

		var pw = 0;
		var ph = 0;
		/*o.css("position","absolute");*/
		if (o.get(0).tagName == "BODY") {
			pw = $(window).width();
			ph = $(window).height();
			//mk
		} else {
			pw = o.width();
			ph = o.height();
		}

		var items = options.items;
		var lyMap = {};
		var mag = options.margin;
		if (!mag) {
			mag = 1;
		}
		if (items) {
			for ( var i = 0; i < items.length; ++i) {
				var item = items[i];
				getPannelSize(pw, ph, item, lyMap);
			}
			fitSzie(pw, ph, lyMap, mag);
			for ( var i = 0; i < items.length; ++i) {
				var item = items[i];
				createPannel(o, item, lyMap);
				arrangePannel(mag, o, item, lyMap);
			}
		}
	};

	var fitSzie = function(pw, ph, lyMap, mag) {

		var ch = ph;
		var cw = pw;
		if (lyMap["top"]) {
			lyMap["top"].w = pw - (2 + 2 * mag);
			ch = ch - lyMap["top"].h;
			lyMap["top"].h = lyMap["top"].h - (2 + mag);
		} else {
			lyMap["top"] = {};
			lyMap["top"].w = 0;
			lyMap["top"].h = 0;
		}

		if (lyMap["bottom"]) {
			lyMap["bottom"].w = pw - (2 + 2 * mag);
			ch = ch - lyMap["bottom"].h;
			lyMap["bottom"].h = lyMap["bottom"].h - (2 + 2 * mag);
		} else {
			lyMap["bottom"] = {};
			lyMap["bottom"].w = 0;
			lyMap["bottom"].h = 0;
		}
		if (ch == ph || lyMap["bottom"].h == 0) {
			if(lyMap["top"].h==0){
				ch = ch - (2 + 2 * mag);
			}else{
				ch = ch - (3 + 2 * mag);
			}
		} else {
			ch = ch - (2 + mag);
		}

		if (lyMap["left"]) {
			lyMap["left"].h = ch;
			cw = cw - lyMap["left"].w;
			lyMap["left"].w = lyMap["left"].w - (2 + mag);
		} else {
			lyMap["left"] = {};
			lyMap["left"].w = 0;
			lyMap["left"].h = 0;
		}
		if (lyMap["right"]) {
			lyMap["right"].h = ch;
			if (lyMap["center"]) {
				cw = cw - lyMap["right"].w;
				lyMap["right"].w = lyMap["right"].w - (2 + mag);
			} else {
				lyMap["right"].w = cw - (2 + mag * 2);
			}
		} else {
			lyMap["right"] = {};
			lyMap["right"].w = 0;
			lyMap["right"].h = 0;
		}
		if (lyMap["center"]) {
			lyMap["center"].h = ch;
			lyMap["center"].w = cw - (2 + mag * 2);
		}else {
			lyMap["center"] = {};
			lyMap["center"].w = 0;
			lyMap["center"].h = 0;
		}
		
	};

	var getPannelSize = function(pw,ph, item, lyMap) {
		if (item) {
			if (item.region) {
				var w=0;
				var h=0;
				lyMap[item.region] = {};
				if (valiNum(item.width)) {
					w = pw * item.width;
				} else {
					w = item.width;
				}
				if (valiNum(item.height)) {
					h = ph * item.height;
				} else {
					h = item.height;
				}
				lyMap[item.region].w = w;
				lyMap[item.region].h = h;
			}
			
		}
	};
	
	var createPannel=function(o, item, lyMap){
		if (item && item.region) {
		var fo = o.get(0);
		var pid = item.id;
		var divo = $.newEl("div");
		divo.region=item.region;
		if (!pid) {
			pid = item.region + "_" + $.getRandom(1000);
			item.id = pid;
		}
		divo.id = pid;
		fo.appendChild(divo);
		item.width = lyMap[item.region].w;
		item.height = lyMap[item.region].h;
		$("#" + pid).pannel(item);
		}
	};

	var valiNum = function(num) {
		return valiReg.test(num);
	};

	var arrangePannel = function(mag, o, item, lyMap) {
		var po = $("#" + item.id);
		var pt = 0;
		var pl = 0;
		if (item.region == "top") {
			pt = mag;
			pl = mag;
		} else if (item.region == "left") {
			pl = mag;
			if (lyMap['top'].h == 0) {
				pt = mag;
			} else {
				pt = 2 + 2 * mag + lyMap['top'].h;
			}
		} else if (item.region == "center") {
			if(lyMap['left'].w==0 ){
				pl=mag;
			}else{
				pl = 2 + 2 * mag + lyMap['left'].w;
			}
			if (lyMap['top'].h == 0) {
				pt = mag;
			} else {
				pt = 2 + 2 * mag + lyMap['top'].h;
			}
		} else if (item.region == "right") {
			if(lyMap['left'].w!=0 && lyMap['center'].w!=0 ){
				pl = 4 + 3 * mag + lyMap['left'].w + lyMap['center'].w;
			}else if( lyMap['center'].w==0 && lyMap['left'].w==0){
				pl =  mag;
			}else{
				pl = 2 + 2 * mag + lyMap['left'].w + lyMap['center'].w;
			}
			if (lyMap['top'].h == 0) {
				pt = mag;
			} else {
				pt = 2 + 2 * mag + lyMap['top'].h;
			}
		} else if (item.region == "bottom") {
			var ch = 0;
			if (lyMap['left'].h>0) {
				ch= lyMap['left'].h;
			} 
			if (lyMap['center'].h>0) {
				ch = lyMap['center'].h;
			}
			if (lyMap['right'].h>0) {
				ch = lyMap['right'].h;
			}
			if(ch==0){
				pt = 2 + 2 * mag + lyMap['top'].h + ch;
			}else{
				pt = 4 + 3 * mag + lyMap['top'].h + ch;
			}
			pl = mag;
		}
		po.css({"top":pt + "px","left":pl + "px"});
	};
	

})(jQuery);
