<#macro pageBar actionUrl replaceListDivId replacePageDivId params>
	<div id="${replacePageDivId}" class="A_PageChange">
	    <ul style="float:left"><li>总共<a>${page.total}</a>条记录</li></ul>
		<ul>
	    	<li><a href="${actionUrl}?page=${page.lastPage}&${params}" actionType="ajaxLink" script="|replace(${replaceListDivId},${replaceListDivId});replace(${replacePageDivId},${replacePageDivId})|">尾页</a></li>
	    	<#if !page.isLast() >
	        	<li><a href="${actionUrl}?page=${page.page + 1}&${params}" actionType="ajaxLink" script="|replace(${replaceListDivId},${replaceListDivId});replace(${replacePageDivId},${replacePageDivId})|">后一页</a></li>
	        <#else>
	        	<li>后一页</li>
	        </#if>
	        <li><span> ${page.page + 1} </span> 页</li>
	        
	        <#if !page.isFirst() >
	        	<li><a href="${actionUrl}?page=${page.page - 1}&${params}" actionType="ajaxLink" script="|replace(${replaceListDivId},${replaceListDivId});replace(${replacePageDivId},${replacePageDivId})|">前一页</a></li>
	        <#else>
	        	<li>前一页</li>
	        </#if>
	        
	    	<li><a href="${actionUrl}?page=0&${params}" actionType="ajaxLink" script="|replace(${replaceListDivId},${replaceListDivId});replace(${replacePageDivId},${replacePageDivId})|"> 首页 </a> </li>
	    </ul>
	</div>
</#macro>