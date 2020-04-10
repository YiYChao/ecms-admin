<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>商品列表--${site.name}</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel = "shortcut icon" href="${site.logo}">
    <link rel="stylesheet" href="${base}/static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${base}/static/css/user.css" media="all" />
    <style>
        .layui-table-cell{
            height:auto !important;
            line-height:auto !important;
        }
    </style>
</head>
<body class="childrenBody">
<fieldset class="layui-elem-field">
    <legend>资源检索</legend>
    <div class="layui-field-box">
    <form class="layui-form">
        <div class="layui-inline" style="margin-left: 15px">
            <label>商品品牌:</label>
            <div class="layui-input-inline">
                <select name="s_source">
                    <option value="">请选择商品品牌</option>
                    <@my type="sys_rescource_source">
                        <#list result as r>
                    <option value="${r.value}" >${r.label}</option>
                        </#list>
                    </@my>
                </select>
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 15px">
            <label>商品编码:</label>
            <div class="layui-input-inline">
                <input type="text" value="" name="s_type" placeholder="请输入商品编码" class="layui-input search_input">
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 15px">
            <label>hash值:</label>
            <div class="layui-input-inline">
                <input type="text" value="" name="s_hash" placeholder="请输入搜索关键词" class="layui-input search_input">
            </div>
        </div>
        <div class="layui-inline">
            <a class="layui-btn" lay-submit="" lay-filter="searchForm">查询</a>
        </div>
        <div class="layui-inline" >
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
        <div class="layui-inline">
            <a class="layui-btn layui-btn-danger" data-type="deleteSome">批量导出</a>
        </div>
    </form>
    </div>
</fieldset>
<div class="layui-form users_list">
    <table class="layui-table" id="test" lay-filter="demo"></table>

    <script type="text/html" id="source">
        <@my type="sys_rescource_source">
            <#list result as r>
            {{#  if(d.source == '${r.value}'){ }}
            <span>${r.label}</span>
            {{#  } }}
            </#list>
        </@my>
    </script>
    <script type="text/html" id="imgUrl">
        <span class="layer-photos-demo" id="pic_{{d.id}}"><img src="{{ d.imgUrl }}" layer-src="{{ d.imgUrl }}" style="width: 60px; height: auto" lay-event="showPic" ></span>
    </script>
</div>
<div id="page"></div>
<div id='jsonContainer' class="Canvas" style="display: none"></div>
<script>
    var baseDir = '${base}';
</script>
<script type="text/javascript" src="${base}/static/layui/layui.js"></script>
<script type="text/javascript" src="${base}/static/js/tools.js"></script>
<script>
    layui.use(['layer','form','table'], function() {
        var layer = layui.layer,
                $ = layui.jquery,
                form = layui.form,
                table = layui.table,
                t;                  //表格数据变量

        t = {
            elem: '#test',
            url:'${base}/admin/product/list',
            method:'post',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                groups: 5, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
                limits:[10, 15, 20, 30]
            },
            width: $(parent.window).width()-223,
            cols: [[
                {type:'checkbox'},
                {field:'id', title: 'ID', width:60,  sort: true},
                {field:'imgUrl', title: '商品图片', width: 100, templet:'#imgUrl'},
                {field:'code',  title: '商品编码', width: 130},
                {field:'title', title: '商品名称'},
                {field:'category', title: '商品分类', width: 160},
                {field:'brand',  title: '商品品牌', width: 160},
                {field:'createDate', title: '商品发布日期', width: 160, templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true},
                {field:'pdtStatus', title: '产品状态', width: 100},
                {field:'contStatus', title: '内容状态', width: 100},
                {field:'price', title: '创建时间', width: 160, templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
            ]]
        };
        table.render(t);

        //监听工具条
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === "del"){
                layer.confirm("你确定要删除该资源么？",{btn:['是的,我确定','我再想想']},
                    function(){
                        $.post("${base}/admin/system/rescource/delete",{"ids":[data.id]},function (res){
                           if(res.success){
                               layer.msg("删除成功",{time: 1000},function(){
                                   table.reload('test', t);
                               });
                           }else{
                               layer.msg(res.message);
                           }

                        });
                    }
                )
            }
            if(obj.event === "showPic"){
                layer.photos({
                    photos: '#pic_'+data.id,
                    anim: 5
                });
            }
        });

        //功能按钮
        var active={
            deleteSome : function(){                        //批量删除
                var checkStatus = table.checkStatus('test'),
                     data = checkStatus.data;
                if(data.length > 0){
                    layer.confirm("你确定要导出这些资源么？",{btn:['是的,我确定','我再想想']},
                        function(){
                            var d = [];
                            for(var i=0;i<data.length;i++){
                                d.push(data[i].id);
                            }
                            $.post("${base}/admin/system/product/export",{ids:d},function (res) {
                                if(res.success){
                                    layer.msg("导出成功",{time: 1000},function(){
                                        table.reload('test', t);
                                    });
                                }else{
                                    layer.msg(res.message);
                                }
                            });
                        }
                    )
                }else{
                    layer.msg("请选择需要导出的商品",{time:1000});
                }
            }
        };

        $('.layui-inline .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //搜索
        form.on("submit(searchForm)",function(data){
            t.where = data.field;
            table.reload('test', t);
            return false;
        });

    });
</script>
</body>
</html>