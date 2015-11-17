<#-- 截取字符串 -->
<#macro substring str count>
  <#if str?length != 0>
    <#assign name = str>
    <#assign end = (!name?? || name?length lt count)?string((name?length-1)!0, count-1)>
    ${(name[0..end?number])}
    <#if name?length gt count>...</#if>
  </#if>
</#macro>