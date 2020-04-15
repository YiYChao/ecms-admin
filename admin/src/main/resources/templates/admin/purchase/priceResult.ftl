<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>定价结果列表--${site.name}</title>
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
            <label>文件Id:</label>
            <div class="layui-input-inline" style="width: 140px">
                <input type="text" value="" name="sid" placeholder="请输入文件Id" class="layui-input search_input">
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 15px">
            <label>品牌：</label>
            <div class="layui-input-inline">
                <input type="text" value="" name="brand" placeholder="请输入询价品牌" class="layui-input search_input">
            </div>
        </div>
        <div class="layui-inline" style="margin-left: 15px">
            <label>型号</label>
            <div class="layui-input-inline">
                <input type="text" value="" name="type" placeholder="请输入询价型号" class="layui-input search_input">
            </div>
        </div>
        <div class="layui-inline">
            <a class="layui-btn" lay-submit="" lay-filter="searchForm">查询</a>
        </div>
        <div class="layui-inline" >
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
        <div class="layui-inline" id="export">
            <button type="button" class="layui-btn layui-btn-normal" id="exportBtn"><i class="layui-icon"></i>导出选中结果</button>
        </div>
        <div class="layui-inline" id="export">
            <button type="button" class="layui-btn layui-btn-warm" id="exportAll"><i class="layui-icon"></i>导出全部结果</button>
        </div>
    </form>
    </div>
</fieldset>
<div class="layui-form users_list">
    <table class="layui-table" id="test1" lay-filter="demo1"></table>
    <table class="layui-table" id="test2" lay-filter="demo2"></table>

    <script type="text/html" id="webUrl">
        <span class="layer-photos-demo" id="pic_{{d.id}}"><img src="{{ d.img }}" layer-src="{{ d.img }}" style="width: 40px" lay-event="showPic" ></span>
    </script>
    <script type="text/html" id="download">
        <a class="layui-btn layui-btn layui-btn-xs" href="{{ d.webUrl }}">下载文件</a>
    </script>
    <script type="text/html" id="searchPage">
        <a class="layui-btn layui-btn-warm layui-btn-xs" href="{{ d.searchUrl }}" target="_blank">搜索页面</a>
    </script>
    <script type="text/html" id="productDetail">
        <a class="layui-btn layui-btn-normal layui-btn-xs" href="{{ d.url }}" target="_blank">商品详情</a>
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
                t1, ids=[];                  //表格数据变量

        t1 = {
            elem: '#test1',
            url:'${base}/admin/purchase/price/result/list',
            limit: 1,
            method:'post',
            page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'], //自定义分页布局
                groups: 5, //只显示 1 个连续页码
                first: "首页", //显示首页
                last: "尾页", //显示尾页
            },
            width: $(parent.window).width()-223,
            cols: [[
                {field:'id', title: 'ID',width:70},
                {field:'brand',title: '品牌'},
                {field:'type',title: '型号'},
                {field:'color',title: '颜色'},
                {field:'size',title: '大小'},
                {field:'shape',title: '形状'},
                {field:'spec',title: '规格'},
                {field:'others',title: '其它'},
                {field:'webUrl',title: '文件地址',align: 'center',toolbar: '#download'},
                {field:'searchUrl',title: '搜索链接',align: 'center',toolbar: '#searchPage'},
                {field:'createDate',  title: '创建时间',width:160, templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
            ]],
            done:function (res) {
                if (res.data == null){
                    return false;
                }
                table.render({
                    elem: '#test2',
                    width: $(parent.window).width()-223,
                    limit: 20,
                    cols: [[
                        {type:'checkbox'},
                        {field:'id', title: 'ID',width:70},
                        {field:'img',title: '图片',templet:'#webUrl'},
                        {field:'title',title: '商品标题', width:'40%'},
                        {field:'price',title: '价格'},
                        {field:'url',title: '链接地址',align: 'center',toolbar: '#productDetail'},
                        {field:'createDate', title: '创建时间',width:160, templet:'<div>{{ layui.laytpl.toDateString(d.createDate) }}</div>',unresize: true}, //单元格内容水平居中
                    ]],
                    data: res.data[0].resultList
                });
            }
        };
        table.render(t1);

        //监听工具条
        table.on('tool(demo2)', function(obj){
            var data = obj.data;
            if(obj.event === "showPic"){
                layer.photos({
                    photos: '#pic_'+data.id,
                    anim: 5
                });
            }
        });

        //搜索
        form.on("submit(searchForm)",function(data){
            t1.where = data.field;
            table.reload('test1', t1);
            return false;
        });

        //获取元素在数组的下标
        Array.prototype.indexOf = function(val) {
            for (var i = 0; i < this.length; i++) {
                if (this[i] == val) {
                    return i;
                };
            }
            return -1;
        };
        //根据数组的下标，删除该下标的元素
        Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };

        table.on('checkbox', function(obj){
            if(obj.checked == true){
                ids.push(obj.data.id);
            }else{
                ids.remove(obj.data.id);
            }
        });

       $("#exportBtn").click(function () {
           var url = "${base}/admin/purchase/price/result/exportExcel";
           $('<form method="post" action="'+ url +'"><input name="ids" value="'+ids+'" /></form>').appendTo('body').submit().remove();
        });
        $("#exportAll").click(function () {
            var sid = document.getElementsByName("sid")[0].value;
            console.log(sid)
            var url = "${base}/admin/purchase/price/result/exportExcel";
            $('<form method="post" action="'+ url +'"><input name="sid" value="'+sid+'" /></form>').appendTo('body').submit().remove();
        });
    });
</script>
</body>
</html>