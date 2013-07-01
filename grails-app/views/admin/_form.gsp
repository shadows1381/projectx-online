
<z:rows>

	<z:row>
		<z:label
			value="${message(code:'admin.username.label',default:'Username')}" />
		<z:textbox name="username" value="${adminInstance?.username}" id="usernameBox"/>
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'admin.password.label',default:'Password')}" />
		<z:textbox name="password" value="${adminInstance?.password}"
			type="password" id="passwordBox"/>
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'admin.password.label',default:'Re-Enter Password')}" />
		<z:textbox name="passwordre" value="${adminInstance?.password}"
			type="password" id="passwordReBox"/>
	</z:row>

	<z:row>
		<z:label value="${message(code:'admin.email.label',default:'Email')}" />
		<z:textbox name="email" value="${adminInstance?.email}" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'admin.accountExpired.label',default:'Account Expired')}" />
		<z:checkbox name="accountExpired"
			checked="${adminInstance?.accountExpired}" disabled="true" id="acountExpiredCheck"/>
	</z:row>

	<z:row visible="false">
		<z:label
			value="${message(code:'admin.accountLocked.label',default:'Account Locked')}" />
		<z:checkbox name="accountLocked"
			checked="${adminInstance?.accountLocked}" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'admin.enabled.label',default:'Enabled')}" />
		<z:checkbox name="enabled" checked="${adminInstance?.enabled}"
			disabled="true" id="enabledCheckBox"/>
	</z:row>

	<z:row visible="false">
		<z:label
			value="${message(code:'admin.passwordExpired.label',default:'Password Expired')}" />
		<z:checkbox name="passwordExpired"
			checked="${adminInstance?.passwordExpired}" />
	</z:row>

</z:rows>