/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.book.rebo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Vector;

public class QuickStartActivity extends AppCompatActivity {
	// 华为帐号登录授权服务，提供静默登录接口silentSignIn，获取前台登录视图getSignInIntent，登出signOut等接口
	// Huawei account service, provides silent signIn API silentSignIn, obtain front-end sign-in view API getSignInIntent, sign out API signOut and other APIs
	private AccountAuthService mAuthService;

	// 华为帐号登录授权参数
	// parameter
	private AccountAuthParams mAuthParam;

	// 用户自定义signInIntent请求码
	// User-defined signInIntent request code
	private static final int REQUEST_CODE_SIGN_IN = 1000;

	// 用户自定义日志标记
	// User-defined log mark
	private static final String TAG = "Account";
	private TextView logTextView,signIn;
	EditText NIA ,password;
	Vector<User> userList = new Vector<>();
	Vector<User> isAuthor = new Vector<>();
	boolean isLogged = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_huawei_quickstart);


			SharedPreferences sharedPreferences = getSharedPreferences("isAuthourized", Context.MODE_PRIVATE);
			if(sharedPreferences.getAll().isEmpty()){
				System.out.println("shared"+sharedPreferences.getAll().isEmpty());

			}else{
				Gson gson = new Gson();

				String json =  sharedPreferences.getString("Data",null);

				Type type = new TypeToken<Vector<User>>(){}.getType();

				isAuthor = gson.fromJson(json,type);

				if(!isAuthor.isEmpty()) {

					Intent directHome = new Intent(getApplicationContext(), HomeActivity.class);
					startActivity(directHome);
				}
			}




		findViewById(R.id.HuaweiIdAuthButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				silentSignInByHwId();
			}
		});

		NIA = (EditText)findViewById(R.id.NIA);
		password = (EditText)findViewById(R.id.password);
		findViewById(R.id.logIn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				logIn(NIA,password);
			}
		});

//		Sign in button
		sign_inAccount(signIn);
		findViewById(R.id.HuaweiIdSignOutButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				signOut();
			}
		});

		findViewById(R.id.HuaweiIdCancelAuthButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelAuthorization();
			}
		});
//		logTextView = (TextView) findViewById(R.id.LogText);
	}
	public void sign_inAccount(TextView signIn){
		TextView signIn_text = findViewById(R.id.signIn);
		String text = "Don't have an account? Sign In here";
		SpannableString ss = new SpannableString(text);
		ClickableSpan sign_in = new ClickableSpan() {
			@Override
			public void onClick(@NonNull View view) {
				Toast.makeText(QuickStartActivity.this,"Sign In here",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void updateDrawState(@NonNull TextPaint ds) {
				super.updateDrawState(ds);
				ds.setColor(Color.BLUE);
			}
		};
		ss.setSpan(sign_in,23,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		signIn_text.setText(ss);
		signIn_text.setMovementMethod(LinkMovementMethod.getInstance());

		signIn_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent gotoSignin = null;
				gotoSignin = new Intent(getApplicationContext(),SignInActivity.class);
				startActivity(gotoSignin);
			}
		});
	}
	/**
	 * 静默登录，如果设备上的华为帐号系统帐号已经登录，并且用户已经授权过，无需再拉起登录页面和授权页面，
	 * 将直接静默登录成功，在成功监听器中，返回帐号信息;
	 * 如果华为帐号系统帐号未登录或者用户没有授权，静默登录会失败，需要显示拉起前台登录授权视图。
	 * Silent sign in, if the HUAWEI ID system account on the device has been logged in and
	 * the user has been authorized, there is no need to pull up the login page and authorization page,
	 * and the silent login will be successful. In the success monitor, the account information will be returned;
	 * If the HUAWEI ID system account is not logged in or the user is not authorized, the silent login will fail,
	 * and the front-end login authorization view needs to be displayed.
	 */
	private void silentSignInByHwId() {
		System.out.println("masuk huawei");
		// 1、配置登录请求参数AccountAuthParams，包括请求用户id(openid、unionid)、email、profile（昵称、头像）等。
		// 2、DEFAULT_AUTH_REQUEST_PARAM默认包含了id和profile（昵称、头像）的请求。
		// 3、如需要请求获取用户邮箱，需要setEmail();
		// 1. Configure the login request parameters AccountAuthParams, including the requested user id (openid, unionid),
		// email, profile (nickname, avatar), etc.
		// 2. DEFAULT_AUTH_REQUEST_PARAM includes requests for id and profile (nickname, avatar) by default.
		// 3. If you need to get the user mailbox again, you need setEmail();
		mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
				.setEmail()
				.createParams();

		// 使用请求参数构造华为帐号登录授权服务AccountAuthService
		// Use request parameters to construct a Huawei account login authorization service AccountAuthService
		mAuthService = AccountAuthManager.getService(this, mAuthParam);

		// 使用静默登录进行华为帐号登录
		// Use silent sign in for HUAWEI ID login
		Task<AuthAccount> task = mAuthService.silentSignIn();
		task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
			@Override
			public void onSuccess(AuthAccount authAccount) {
				// 静默登录成功，处理返回的帐号对象AuthAccount，获取帐号信息
				// Silent sign in is successful, the returned account object AuthAccount is processed,account information is obtained and processed
				showLog("silent sign in success");
				dealWithResultOfSignIn(authAccount);
			}
		});
		task.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(Exception e) {
				// 静默登录失败，使用getSignInIntent()方法进行前台显式登录
				// Silent sign in fails, use the getSignInIntent() method to log in from the foreground
				if (e instanceof ApiException) {
					ApiException apiException = (ApiException) e;
					Intent signInIntent = mAuthService.getSignInIntent();
					startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);

				}
			}
		});
	}

	/**
	 * 处理返回的AuthAccount，获取帐号信息
	 * Process the returned AuthAccount and get account information
	 *
	 * @param authAccount AccountAccount对象，用于记录帐号信息(AccountAccount object, used to record account information)
	 */
	private void dealWithResultOfSignIn(AuthAccount authAccount) {
		//获取帐号信息
		Log.i(TAG, "display name:" + authAccount.getDisplayName());
		Log.i(TAG, "photo uri string:" + authAccount.getAvatarUriString());
		Log.i(TAG, "photo uri:" + authAccount.getAvatarUri());
		Log.i(TAG, "email:" + authAccount.getEmail());
		Log.i(TAG, "openid:" + authAccount.getOpenId());
		Log.i(TAG, "unionid:" + authAccount.getUnionId());
		showLog("display name:" + authAccount.getDisplayName() + "photo uri string:" + authAccount.getAvatarUriString() +
				"email:" + authAccount.getEmail() + "openid:" + authAccount.getOpenId() + "unionid:" + authAccount.getUnionId());
		// TODO 获取用户信息后业务逻辑
		// TODO Business logic after obtaining user information

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_SIGN_IN) {
			Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
			Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
			if (authAccountTask.isSuccessful()) {
				showLog("sign in success");
				// 登录成功，获取到登录帐号信息对象authAccount
				// The login is successful, and the login account information object authAccount is obtained
				AuthAccount authAccount = authAccountTask.getResult();
				dealWithResultOfSignIn(authAccount);
				Log.i(TAG, "onActivitResult of sigInInIntent, request code: " + REQUEST_CODE_SIGN_IN);
			} else {
				// 登录失败，status code标识了失败的原因，请参考API中的错误码参考了解详细错误原因
				// Login failed. The status code identifies the reason for the failure. Please refer to the error
				// code reference in the API for detailed error reasons.
				showLog("sign in failed : " + ((ApiException) authAccountTask.getException()).getStatusCode());
				Log.e(TAG, "sign in failed : " + ((ApiException) authAccountTask.getException()).getStatusCode());
			}
		}
	}

	private void logIn(EditText NIM, EditText password){
		//lOAD
		SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);

		Gson gson = new Gson();

		String json =  sharedPreferences.getString("Data SignIn",null);
		Type type = new TypeToken<Vector<User>>(){}.getType();

		userList = gson.fromJson(json,type);

		if(userList.isEmpty()){
			userList = new Vector<>();
		}else{
			boolean flag = false;
			for(User data:userList){
				if(NIM.getText().toString().equalsIgnoreCase(data.getUserName()) && password.getText().toString().equals(data.getPassword())){
//			showLog("Berhasil login");
					System.out.println("berhasil");
					Intent login = new Intent(getApplicationContext(),HomeActivity.class);
					Toast.makeText(QuickStartActivity.this,"Welcome to ReBo",Toast.LENGTH_SHORT).show();
					flag = true;
//					Already login
					isLogged = true;
					SharedPreferences prefs = getSharedPreferences("isAuthourized", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					Gson gson2 = new Gson();
					String json2 = gson2.toJson(userList);
					editor.putString("Data",json2);
					editor.apply();
					startActivity(login);
				}
			}
			if(flag == false){
				Toast.makeText(QuickStartActivity.this,"Username and Password is not in our data!",Toast.LENGTH_SHORT).show();
			}

		}


	}



	private void signOut() {
		if (mAuthService == null) {
			return;
		}
		Task<Void> signOutTask = mAuthService.signOut();
		signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				Log.i(TAG, "signOut Success");
				showLog("signOut Success");
			}
		}).addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(Exception e) {
				Log.i(TAG, "signOut fail");
				showLog("signOut fail");
			}
		});
	}

	private void cancelAuthorization() {
		if (mAuthService == null) {
			return;
		}
		Task<Void> task = mAuthService.cancelAuthorization();
		task.addOnSuccessListener(new OnSuccessListener<Void>() {
			@Override
			public void onSuccess(Void aVoid) {
				Log.i(TAG, "cancelAuthorization success");
				showLog("cancelAuthorization success");
			}
		});
		task.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(Exception e) {
				Log.i(TAG, "cancelAuthorization failure：" + e.getClass().getSimpleName());
				showLog("cancelAuthorization failure：" + e.getClass().getSimpleName());
			}
		});
	}

	private void showLog(String log) {
//		logTextView.setText("log:" + "\n" + log);
	}


}
