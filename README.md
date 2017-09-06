# Mule Applications Validation Plugin

Maven plugin for ensuring the consistency of Mule Applications through a set of rules that let you define which elements are allowed or not.

## Plugin Configuration

Each project must have the following plugin configuration in the **pom.xml** file.

```xml
<plugin>
    <groupId>com.mulesoft.services</groupId>
  	<artifactId>mule-validation-maven-plugin</artifactId>
  	<version>1.0-SNAPSHOT</version>
  	<configuration>
  	    <rulesPath>/Users/andresalleva/validation-tool/rules-path</rulesPath>
  		<rulesPattern>^apps-rules(.*).xml</rulesPattern>
  	</configuration>
  	<executions>
  	    <execution>
  		    <phase>compile</phase>
  			<goals>
  			    <goal>validate</goal>
  			</goals>
  		</execution>
  	</executions>
</plugin>
```

### Configurable properties

| Name         | Required | Description | Default Value |
|--------------|:----------:|-------------|---------------:|
| rulesPath    | Yes      | Path to the folder containing the validation rules.|               |
| rulesPattern | No       | Regexp of the validation rules file name. | ^apps-rules(.*).xml |
| sourcePath   | No       | Path to the Mule Project folder containing. the configuration files | /src/main/app |
| configFilePattern   | No       | Regexp of the configuration file name. | ^(.*).xml |

## Validation Rules

Validation Rules are grouped into two main categories: **"Inclusive"** and **"Exclusive"**.

Each **rule** has the following structure:

```xml
<rule>
	<id></id>
	<name></name>
	<type></type>
	<node>
	    <namespace></namespace>
		<name></name>
	</node>
	<inclusive></inclusive>
	<attributes>
		<attribute name="" value="" />
	</attributes>
</rule>
```

| Name          | Description                    | Required | Example |
|---------------|--------------------------------|---------|----------|
| id | Unique identifier of the rule. | yes | RULE_001 |
| name | Brief description of the rule. | yes | Credentials and resources should be managed with application properties |
| type | Scope of the validation rule (configuration or element) | yes | configuration |
| node/namespace | Specifies the XML node namespace. | yes | context |
| node/name | Specifies the XML node name. | yes | property-placeholder |
| inclusive | Specifies if the rule is 'inclusive' or 'exclusive' (true or false). | yes | true |
| attributes | Collection of attributes that the XML node should or should not have with an specific value| no |  |

##### Sample Inclusive Rule

```xml
<rule>
    <id>RULE_004</id>
	<name>API Autodiscovery should be configured</name>
	<type>configuration</type>
	<node>
	    <namespace>api-platform-gw</namespace>
		<name>api</name>
	</node>
	<inclusive>true</inclusive>
</rule>
```

**RULE_004** will fail if API Autodiscovery has not been configured:

`[ERROR] RULE_004 API Autodiscovery should be configured (FAIL).`

##### Sample Exclusive Rule

```xml
<rule>
    <id>RULE_006</id>
	<name>Session Variables should be avoided</name>
	<type>element</type>
	<node>
	    <namespace></namespace>
		<name>set-session-variable</name>
	</node>
	<inclusive>false</inclusive>
</rule>
```

**RULE_006** will fail if the Mule App contains a _Session Variable_ like:

`<set-session-variable variableName="varA" value="#['A']" doc:name="Session Variable"/>`

`[ERROR] RULE_006 Session Variables should be avoided (FAIL).`

### Extending Rules Configuration

#### Checking Attributes

Additionally, you can check for the existance or not of an element with a specific bunch of properties.

##### Sample Inclusive Rule with properties

```xml
<rule>
	<id>RULE_003</id>
	<name>HTTP Request Connector should implement HTTPS protocol</name>
	<type>configuration</type>
	<node>
		<namespace>http</namespace>
		<name>request-config</name>
	</node>
	<inclusive>true</inclusive>
	<attributes>
		<attribute name="protocol" value="HTTPS" />
	</attributes>
</rule>
```

**RULE_003** will fail if the _HTTP Request Connector_ protocol is not set as _HTTPS_ like:

`<http:request-config name="HTTP_Request_Configuration" protocol="HTTP" host="${users.host}" port="${users.port}" doc:name="HTTP Request Configuration"/>`

`[ERROR] RULE_003 HTTP Request Connector should implement HTTPS protocol - Attribute protocol with value HTTPS it's required. (FAIL).`

##### Sample Exclusive Rule with properties

```xml
<rule>
	<id>RULE_003</id>
	<name>HTTP Request Connector should implement HTTPS protocol</name>
	<type>configuration</type>
	<node>
		<namespace>http</namespace>
		<name>request-config</name>
	</node>
	<inclusive>false</inclusive>
	<attributes>
		<attribute name="protocol" value="HTTP" />
	</attributes>
</rule>
```

**RULE_003** will fail if the _HTTP Request Connector_ protocol is set as _HTTP_ like:

`<http:request-config name="HTTP_Request_Configuration" protocol="HTTP" host="${users.host}" port="${users.port}" doc:name="HTTP Request Configuration"/>`

`[ERROR] RULE_003 HTTP Request Connector should implement HTTPS protocol - Attribute protocol with value HTTP it's not allowed. (FAIL).`

#### Checking Naming Conventions

Finally, an **Inclusive Rule with attributes** can be used in conjunction with a Regular Expression for checking naming conventions.

##### Sample Naming Convention over Flow

```xml
<rule>
	<id>RULE_010</id>
	<name>Flow names should match the naming convention</name>
	<type>element</type>
	<node>
		<namespace></namespace>
		<name>flow</name>
	</node>
	<inclusive>true</inclusive>
	<attributes>
		<attribute name="name" value="^[a-z:\/{}]+(-[a-z]+)*$" />
	</attributes>
</rule>
```

Given the following flow:
```xml
<flow name="getUsersFlow">
    <ee:cache cachingStrategy-ref="Caching_Strategy_Managed" doc:name="Cache">
        <flow-ref name="find-users-flow" doc:name="find-users-flow"/>
    </ee:cache>
    <dw:transform-message doc:name="map JSON response">
        <dw:set-payload resource="classpath:mappings/map-to-json.dwl"/>
    </dw:transform-message>
    <logger message="#[message.payloadAs(java.lang.String)]" level="INFO" doc:name="log users"/>
 </flow>
```

The validation will fail with:
`[ERROR] RULE_010 Flow names should match the naming convention - Flow with name 'getUsersFlow' matches '^[a-z:\/{}]+(-[a-z]+)*$' (FAIL).`

##### Sample Naming Convention over Sub-flow

## Contribute
Enjoy and provide feedback / contribute :)
