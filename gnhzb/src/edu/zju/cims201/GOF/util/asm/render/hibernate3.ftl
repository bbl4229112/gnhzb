<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<hibernate-mapping>    
    <class name="${entity.className}" table="${entity.tableName}">
    	<#if entity.properties?exists>
        
    	<!-- loop begin -->
			<#list entity.properties as property>
			
				<property name="${property.name}" type="${property.type}">

				<#if property.type=="java.util.Date">
					<column name="${property.name?upper_case}" <#if !property.canEmpty> not-null="true" <#else> not-null="false"  </#if> ></column>
				<#else >
					<column name="${property.name?upper_case}" <#if property.length?exists>length="${property.length}"</#if> <#if !property.canEmpty> not-null="true" <#else> not-null="false"  </#if> ></column>
				</#if>
				
			   </property>
			
			</#list>
			<!-- loop end -->
			
		</#if>
		<!-- end if exists -->
		
    </class>
    
</hibernate-mapping>
