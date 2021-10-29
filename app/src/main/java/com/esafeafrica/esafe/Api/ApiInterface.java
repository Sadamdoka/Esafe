package com.esafeafrica.esafe.Api;



import com.esafeafrica.esafe.Model.AmnestyList;
import com.esafeafrica.esafe.Model.AnounceList;
import com.esafeafrica.esafe.Model.AnounceSingle;
import com.esafeafrica.esafe.Model.CasesList;
import com.esafeafrica.esafe.Model.CasesSingle;
import com.esafeafrica.esafe.Model.ContactList;
import com.esafeafrica.esafe.Model.CoronaList;
import com.esafeafrica.esafe.Model.CoronaSingle;
import com.esafeafrica.esafe.Model.DonateList;
import com.esafeafrica.esafe.Model.EmergencyList;
import com.esafeafrica.esafe.Model.EmergencySingle;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.NumbersList;
import com.esafeafrica.esafe.Model.NumbersSingle;
import com.esafeafrica.esafe.Model.OrganList;
import com.esafeafrica.esafe.Model.PressList;
import com.esafeafrica.esafe.Model.UserWorkerSingle;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by sadam on 08/08/18.
 * Copyright 2018
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public interface ApiInterface {


    @Multipart
    @POST("conditions/worker")
    Call<Feedback> Authenticate(@Part("nin") RequestBody nin);

    @Multipart
    @POST("create/guest")
    Call<Feedback> createAccount(@Part("name") RequestBody name, @Part("pass") RequestBody pass,@Part("address") RequestBody address,@Part("email") RequestBody email,@Part("phone") RequestBody phone, @Part("gender") RequestBody gender, @Part("dob") RequestBody dob, @Part("jobtype") RequestBody jobtype,@Part("company") RequestBody company,@Part("loca") RequestBody loca,@Part("lati") RequestBody lati,@Part("longi") RequestBody longi,@Part MultipartBody.Part pic);

    @Multipart
    @POST("create/cases")
    Call<Feedback> createCase(@Part("case") RequestBody _case, @Part("recovery") RequestBody recovery, @Part("dead") RequestBody dead, @Part("last") RequestBody last);

    @Multipart
    @POST("create/corona")
    Call<Feedback> createCorona(@Part("name") RequestBody name, @Part("lati") RequestBody lati, @Part("longi") RequestBody longi, @Part("tel") RequestBody tel, @Part("travel") RequestBody travel, @Part("were") RequestBody were, @Part("pain") RequestBody pain, @Part("fever") RequestBody fever, @Part("cough") RequestBody cough, @Part("sore") RequestBody sore, @Part("nose") RequestBody nose, @Part("tired") RequestBody tired, @Part("breath") RequestBody breath);


    @Multipart
    @POST("create/emergency")
    Call<Feedback> createEmergency(@Part("name") RequestBody name, @Part("userid") RequestBody userid,@Part("pass") RequestBody pass,@Part("organ") RequestBody organ,@Part("topic") RequestBody topic,@Part("loca") RequestBody loca,@Part("lati") RequestBody lati, @Part("longi") RequestBody longi, @Part("event") RequestBody event, @Part("details") RequestBody details, @Part("former") RequestBody former, @Part MultipartBody.Part pic);

    @Multipart
    @POST("create/contact")
    Call<Feedback> createContact(@Part("userid") RequestBody userid, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("tel") RequestBody tel);

    @Multipart
    @POST("update/emergency")
    Call<Feedback> updateEmergency(@Part("id") RequestBody id, @Part("name") RequestBody name, @Part("lati") RequestBody lati, @Part("longi") RequestBody longi, @Part("brief") RequestBody brief, @Part("event") RequestBody event, @Part("extras") RequestBody extras);

    @Multipart
    @POST("update/account")
    Call<Feedback> updateAccount(@Part("id") RequestBody id, @Part("userid") RequestBody userid, @Part("name") RequestBody name, @Part("pass") RequestBody pass, @Part("ptpic") RequestBody ptpic, @Part("lco") RequestBody lco,@Part("fco") RequestBody fco,@Part("email") RequestBody email,@Part("tel") RequestBody tel,@Part("password") RequestBody password,@Part("pic") RequestBody pic,@Part("loca") RequestBody loca,@Part("lati") RequestBody lati,@Part("longi") RequestBody longi);

    @Multipart
    @POST("update/contact")
    Call<Feedback> updateContact(@Part("id") RequestBody id, @Part("userid") RequestBody userid, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("tel") RequestBody tel);

    @Multipart
    @POST("update/loca")
    Call<Feedback> updateLoca(@Part("nin") RequestBody nin,@Part("loca") RequestBody loca,@Part("lati") RequestBody lati,@Part("longi") RequestBody longi);

    @GET("fetch/emergency/{id}/{emerid}/{userid}/{passport}/{organ}")
    Call<EmergencyList> getEmergencyList(@Path("id") String id,@Path("emerid") String emerid,@Path("userid") String userid,@Path("passport") String passport,@Path("organ") String organ);

    @GET("fetch/emergency/{id}/{emerid}/{userid}/{passport}/{organ}")
    Call<EmergencySingle> getEmergencySingle(@Path("id") String id, @Path("emerid") String emerid, @Path("userid") String userid, @Path("passport") String passport, @Path("organ") String organ);

    @GET("fetch/worker/{id}")
    Call<UserWorkerSingle> getAccount(@Path("id") String id);

    @GET("fetch/workers/{con}")
    Call<UserWorkerSingle> getAccountCon(@Path("con") String con);

    @GET("fetch/em_number")
    Call<NumbersList> allNumbers();

    @GET("fetch/em_number")
    Call<NumbersSingle> allNumber();

    @GET("fetch/contact")
    Call<ContactList> allContacts();

    @GET("fetch/anounce")
    Call<AnounceList> allAnounce();

    @GET("fetch/anounce")
    Call<AnounceSingle> allAnounceSingle();


    @GET("fetch/amnesty")
    Call<AmnestyList> allAmnesty();

    @GET("fetch/corona")
    Call<CoronaList> allCorona();

    @GET("fetch/corona/{id}")
    Call<CoronaSingle> getCorona(@Path("id") String id);

    @GET("fetch/cases")
    Call<CasesList> allCases();

    @GET("fetch/case/{id}")
    Call<CasesSingle> getCases(@Path("id") String id);

    @GET("fetch/press")
    Call<PressList> allPress();

    @GET("fetch/press/{id}")
    Call<PressList> getPress(@Path("id") String id);


    @GET("fetch/donate")
    Call<DonateList> allDonate();

    @GET("fetch/donate/{id}")
    Call<DonateList> getDonate(@Path("id") String id);

    @GET("fetch/organ")
    Call<OrganList> allOrgan();


    @GET("fetch/organs/{type}")
    Call<OrganList> Organtype(@Path("type") String type);

    @GET("fetch/organ/{id}/{organid}")
    Call<OrganList> getOrgan(@Path("id") String id,@Path("organid") String organid);



}
