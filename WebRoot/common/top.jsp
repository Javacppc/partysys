
<!-- 头部 -->
<table width="1222" border="0" align="center" cellpadding="0"
  cellspacing="0" <%-- background="${ctx}/images/home/top_bg.png" --%> class="top">
    <tr>
        <td width="32" height="106">&nbsp;</td>
        <td width="418" height="106" align="left" valign="middle">
        
            <img class="zxx_test_png" src="${ctx}/images/home/ind03.png" width="280" height="40" alt="" />
            
        </td>
        <td width="211">&nbsp;</td>
        <td width="328">         
            <%-- 17.4.11 -lxy --%>
            <%-- <object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="450" height="86">
                <param name="movie" value="${ctx}/images/home/flash.swf" />
                <param name="quality" value="high" />
                <param name="wmode" value="transparent" />
                <param name="swfversion" value="6.0.65.0" />
                <!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
                <param name="expressinstall" value="Scripts/expressInstall.swf" />
                <!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="${ctx}/images/home/flash.swf"
                        width="380" height="86">
                    <!--<![endif]-->
                    <param name="quality" value="high" />
                    <param name="wmode" value="transparent" />
                    <param name="swfversion" value="6.0.65.0" />
                    <param name="expressinstall" value="Scripts/expressInstall.swf" />
                    <!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
                    <div>
                        <h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>
                        <p>
                            <a href="http://www.adobe.com/go/getflashplayer"><img
                                    src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
                                    alt="获取 Adobe Flash Player" width="112" height="33" />
                            </a>
                        </p>
                    </div>
                    <!--[if !IE]>-->
                </object> 
                
                <!--<![endif]-->
            </object> --%>      
            
        </td>
        <td width="331" align="right" valign="top">
            <table width="350" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="64" height="4"></td>
                    <td width="25" height="5"></td>
                    <td width="120" height="4"></td>
                    <td width="25" height="4"></td>
                    <td width="40" height="4"></td>
                    <td width="25" height="4"></td>
                    <td width="50" height="4"></td>
                </tr>
                <tr>
                    <td align="center"></td>
                    <td align="left"></td>
                    <td align="right">
                        <a><b></b><font color="red">欢迎您，<s:property value="#session.SYS_USER.name" />&nbsp;</font></a>
                    </td>                 
                    <td align="center"><img src="${ctx}/images/home/help1.png" width="12" height="17"  /></td>
                    <td align="left"><a href="javascript:void(0);"><font color="red">帮助</font></a></td>             
                    <td align="center"><img src="${ctx}/images/home/exit1.png" width="14" height="14"   /></td>
                    <td align="left" valign="middle" ><a href="${ctx }/sys/login_logout.action"><font color="red">退出</font></a></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<!-- 头部 -->
<!-- 导航 -->
<div class="menu">
    <ul class="clearfix">
        <li class="hover"><a href="${basePath}sys/home.action">工作主页</a></li>
        <li><a href="javascript:void(0);">党员学习</a></li>
        <li><a href="javascript:void(0);">民生平台</a></li>
        <li><a href="${ctx }/partymanage/home_frame.action">党建管理</a></li>       
        <li><a href="${ctx }/sysmanage/home_frame.action">系统管理</a></li>
        <!--  <li><a href="javascript:void(0);">我的空间</a></li>-->
    </ul>
</div>