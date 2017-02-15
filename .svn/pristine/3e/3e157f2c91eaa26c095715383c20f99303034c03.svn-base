package com.xpro.ebusalmoner.data;

import java.util.List;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import android.util.Log;

import com.xpro.ebusalmoner.data.TrailerVehicle;
import com.xpro.ebusalmoner.datasource.DatabaseOpenHelper;

public class TrailerVehicleDB {
	private DbManager db;

	// 接收构造方法初始化的DbManager对象
	public TrailerVehicleDB() {
		db = DatabaseOpenHelper.getInstance();
	}

	/****************************************************************************************/
	/**
	 * 将Person实例存进数据库
	 */
	public void savePerson(TrailerVehicle person) {
		try {
			db.save(person);
			Log.e("xyz_success", "save succeed!");
		} catch (DbException e) {
			Log.e("xyz_error", e.toString());
		}
	}

	// 读取所有Person信息
	public List<TrailerVehicle> loadPerson() {
		List<TrailerVehicle> list = null;
		try {
			list = db.selector(TrailerVehicle.class).findAll();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询id=2的数据
	public TrailerVehicle query() {
		try {
			TrailerVehicle person = db.findById(TrailerVehicle.class, "1");
			Log.e("person", person.toString());
			return person;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void delete() {
		try {
			db.dropTable(TrailerVehicle.class);
			Log.e("DB", "删除成功");
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

}
