(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-08b6b669"],{"82bc":function(t,n,e){},cb1a:function(t,n,e){"use strict";e.r(n);var i=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("div",[e("div",{staticClass:"main"},[e("div",{staticClass:"category"},[e("div",{staticClass:"top"},[t._v(" 考试类别 ")]),e("div",{staticClass:"content"},t._l(t.examTypeEnum,(function(n){return e("div",{key:n.enumCode,staticClass:"content-item",class:t.typeEnum===n.enumCode?"active":"",on:{click:function(e){return t.changeExam(n.enumCode)}}},[e("div",{staticClass:"name"},[t._v(" "+t._s(n.msg)+" ")])])})),0)]),e("div",{staticClass:"right-content"},[e("div",{staticClass:"content"},[e("div",{staticClass:"top"},[t._v(" "+t._s(t.examIntroduction.title)+"介绍 ")]),e("div",{staticClass:"line"}),e("div",{staticClass:"center"},[t._v(" "+t._s(t.examIntroduction.description)+" ")]),e("div",{staticClass:"center",staticStyle:{"margin-bottom":"30px"}},[t._v(" "+t._s(t.examIntroduction.rule)+" ")]),e("div",{staticClass:"bottom"},[e("el-button",{attrs:{type:"primary"},on:{click:t.toExamList}},[t._v(" 查看考试安排 ")])],1)])])])])},s=[],a=e("5530"),c=e("2f62"),o={mounted:function(){this.$store.dispatch("getExamTypeEnum","ExamTypeEnum"),this.changeExam(this.typeEnum)},components:{},data:function(){return{typeEnum:"Mandarin"}},computed:Object(a["a"])({},Object(c["b"])({examTypeEnum:function(t){return t.register.examTypeEnum},examIntroduction:function(t){return t.register.examIntroduction}})),methods:{getEexamIntroduction:function(){var t=this,n={typeEnum:this.typeEnum};this.$store.dispatch("getExamIntroduction",n).then((function(n){console.log(t.examIntroduction)}))},changeExam:function(t){this.typeEnum=t,this.getEexamIntroduction()},toExamList:function(){this.$router.push({path:"/register/examList",query:{examType:this.examIntroduction.examType.enumCode}})}}},u=o,r=(e("dc5b"),e("2877")),m=Object(r["a"])(u,i,s,!1,null,"16076b94",null);n["default"]=m.exports},dc5b:function(t,n,e){"use strict";e("82bc")}}]);
//# sourceMappingURL=chunk-08b6b669.b51e798d.js.map