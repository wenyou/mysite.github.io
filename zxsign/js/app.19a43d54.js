(function(e){function t(t){for(var r,i,s=t[0],u=t[1],l=t[2],c=0,p=[];c<s.length;c++)i=s[c],Object.prototype.hasOwnProperty.call(o,i)&&o[i]&&p.push(o[i][0]),o[i]=0;for(r in u)Object.prototype.hasOwnProperty.call(u,r)&&(e[r]=u[r]);f&&f(t);while(p.length)p.shift()();return a.push.apply(a,l||[]),n()}function n(){for(var e,t=0;t<a.length;t++){for(var n=a[t],r=!0,i=1;i<n.length;i++){var u=n[i];0!==o[u]&&(r=!1)}r&&(a.splice(t--,1),e=s(s.s=n[0]))}return e}var r={},o={app:0},a=[];function i(e){return s.p+"js/"+({about:"about"}[e]||e)+"."+{about:"672a5325"}[e]+".js"}function s(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,s),n.l=!0,n.exports}s.e=function(e){var t=[],n=o[e];if(0!==n)if(n)t.push(n[2]);else{var r=new Promise((function(t,r){n=o[e]=[t,r]}));t.push(n[2]=r);var a,u=document.createElement("script");u.charset="utf-8",u.timeout=120,s.nc&&u.setAttribute("nonce",s.nc),u.src=i(e);var l=new Error;a=function(t){u.onerror=u.onload=null,clearTimeout(c);var n=o[e];if(0!==n){if(n){var r=t&&("load"===t.type?"missing":t.type),a=t&&t.target&&t.target.src;l.message="Loading chunk "+e+" failed.\n("+r+": "+a+")",l.name="ChunkLoadError",l.type=r,l.request=a,n[1](l)}o[e]=void 0}};var c=setTimeout((function(){a({type:"timeout",target:u})}),12e4);u.onerror=u.onload=a,document.head.appendChild(u)}return Promise.all(t)},s.m=e,s.c=r,s.d=function(e,t,n){s.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},s.t=function(e,t){if(1&t&&(e=s(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(s.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)s.d(n,r,function(t){return e[t]}.bind(null,r));return n},s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,"a",t),t},s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},s.p="/",s.oe=function(e){throw console.error(e),e};var u=window["webpackJsonp"]=window["webpackJsonp"]||[],l=u.push.bind(u);u.push=t,u=u.slice();for(var c=0;c<u.length;c++)t(u[c]);var f=l;a.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"0e79":function(e,t,n){"use strict";n("dec2")},"56d7":function(e,t,n){"use strict";n.r(t);var r=n("2b0e"),o=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("div",{attrs:{id:"nav"}},[n("router-link",{attrs:{to:"/"}},[e._v("Home")]),e._v(" | "),n("router-link",{attrs:{to:"/about"}},[e._v("About")])],1),n("router-view")],1)},a=[],i=(n("0e79"),n("2877")),s={},u=Object(i["a"])(s,o,a,!1,null,null,null),l=u.exports,c=n("8c4f"),f=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"home"},[n("h1",{staticClass:"mail-title"},[e._v("信息确认")]),n("div",{staticClass:"mail-content"},[e._v("【工会福利】公共交通出行补贴通知,下午好！2022年度“合肥工会会员绿色出行普惠关爱活动”即将开展。自2022年6月18日起至2022年11月18日，凡公司在职工会会员持“合肥通”卡及本人身份证在合肥市94个指定网点，均可享受免费充值50元/人出行补贴【未办理“合肥通”卡的可以前往办理点同时办理，工本费享工会会员优惠价15元/张(标准售价为18元/张）】。")]),n("h2",{staticClass:"mail-title2"},[e._v("接收并签名")]),n("div",{staticClass:"esign-box"},[n("vue-esign",{ref:"esign",attrs:{width:400,height:200,isCrop:e.isCrop,lineWidth:e.lineWidth,lineColor:e.lineColor,bgColor:e.bgColor},on:{"update:bgColor":function(t){e.bgColor=t},"update:bg-color":function(t){e.bgColor=t}}})],1),n("button",{staticClass:"btn",on:{click:e.handleReset}},[e._v("清除，重签")]),n("button",{staticClass:"btn handlebtn",on:{click:e.handleGenerate}},[e._v("完成签名，提交")]),n("div",{staticClass:"resultImg-box"},[n("img",{attrs:{src:e.resultImg}})])])},p=[],d={name:"Home",components:{},data(){return{lineWidth:3,lineColor:"#000000",bgColor:"",resultImg:"",isCrop:!1}},methods:{handleReset(){this.$refs.esign.reset(),this.resultImg=""},handleGenerate(){this.$refs.esign.generate().then(e=>{this.resultImg=e}).catch(e=>{alert("请在画布上进行签名！"),console.error("err:",e)})}}},h=d,b=(n("8c65"),Object(i["a"])(h,f,p,!1,null,"27150e70",null)),v=b.exports;r["a"].use(c["a"]);const m=[{path:"/",name:"Home",component:v},{path:"/about",name:"About",component:()=>n.e("about").then(n.bind(null,"f820"))}],g=new c["a"]({mode:"history",base:"/",routes:m});var y=g,C=n("2f62");r["a"].use(C["a"]);var w=new C["a"].Store({state:{},mutations:{},actions:{},modules:{}}),_=n("13bf");r["a"].config.productionTip=!1,r["a"].use(_["a"]),new r["a"]({router:y,store:w,render:e=>e(l)}).$mount("#app")},"651f":function(e,t,n){},"8c65":function(e,t,n){"use strict";n("651f")},dec2:function(e,t,n){}});
//# sourceMappingURL=app.19a43d54.js.map