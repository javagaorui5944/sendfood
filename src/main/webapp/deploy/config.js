/**
 * 前端部署（包括了对代码的混淆，压缩，和对模板文件的修改，版本号的计算，以及后续CDN的推送等等）配置文件。
 *
 */

'use strict';

// 前端js和css文件的路径
exports.staticFolder = __dirname + '/../static';

// 远程服务器的地址路径
exports.staticServer = '/static';

// 所有的freemaker所在的路径
// exports.freemarkerFolder = [
//     __dirname + '/../WEB-INF/decorators',
//     __dirname + '/../WEB-INF/views' ];

// freemaker整体的配置文件路径
exports.freemarkerConfig = __dirname + '/../WEB-INF/views/config.ftl';

// 目标版本号，如果未定义则默认为Unix时间戳
exports.version = '';

exports.basePath = __dirname;

// ---------------------------------------------------------------------------
// 需要运行的模块列表
var runModules = [];

// 移动所在的文件到目标目录
runModules.push('copy_files');

// 替换掉css中的静态文件图片信息
runModules.push('replace_pictures');

 runModules.push('compress_css');

// 修改freemaker的配置文件
runModules.push('change_config');

// 压缩混淆静态文件,移动静态文件到指定的位置
runModules.push('compress_js');

// 移动静态文件到指定的位置(此模块已移动到compress_js中)
runModules.push('move_static');

exports.runModules = runModules;
// ------------------------------------------------------------------------------