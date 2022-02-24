package com.b07.users;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.b07.exceptions.UnauthorizedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Admin extends User implements Serializable {

  /**
   * Sets the values id, name, age and address for Admin.
   *
   * @param id name age address
   */
  public Admin(int id, String name, int age, String address) {
    this.setId(id);
    this.setName(name);
    this.setAge(age);
    this.setAddress(address);
  }

  /**
   * Sets the values id, name, age and address for Admin if authenticated is true.
   *
   * @param id name age address authenticated
   * @throws UnauthorizedException
   */
  public Admin(int id, String name, int age, String address, boolean authenticated)
          throws UnauthorizedException {
    if (this.getAuthenticated()) {
      this.setId(id);
      this.setName(name);
      this.setAge(age);
      this.setAddress(address);
    } else {
      throw new UnauthorizedException();
    }

  }

  /**
   * Promotes employee to administrator.
   *
   * @param employee
   * @return if the employee has the role id of an administrator
   */
  public boolean promoteEmployee(Employee employee) {
    return (employee.getRoleId() == this.getRoleId());
  }
}
