package com.xpro.ebusalmoner.data;

import java.util.List;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import android.util.Log;

import com.xpro.ebusalmoner.data.HitchVehicle;
import com.xpro.ebusalmoner.datasource.DatabaseOpenHelper;

public class HitchVehicleDB {
	private DbManager db;

	// 接收构造方法初始化的DbManager对象
	public HitchVehicleDB() {
		db = DatabaseOpenHelper.getInstance();
	}

	/****************************************************************************************/
	/**
	 * 将Person实例存进数据库
	 */
	public void savePerson(HitchVehicle person) {
		try {
			db.save(person);
			Log.e("xyz_success", "save succeed!");
		} catch (DbException e) {
			Log.e("xyz_error", e.toString());
		}
	}

	// 读取所有Person信息
	public List<HitchVehicle> loadPerson() {
		List<HitchVehicle> list = null;
		try {
			list = db.selector(HitchVehicle.class).findAll();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询id=2的数据
	public HitchVehicle query() {
		try {
			HitchVehicle person = db.findById(HitchVehicle.class, "1");
			Log.e("person", person.toString());
			return person;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	//该方法主要是用来删除表格里面的所有数据，但是注意：表还会存在，只是表里面数据没有了
	public void delete() {
		try {
			db.dropTable(HitchVehicle.class);
			Log.e("DB", "删除成功");
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

	//该方法是用来删除表
//	private void delete() {
//        try {
//            db.dropTable(HitchVehicle.class);
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }
}
