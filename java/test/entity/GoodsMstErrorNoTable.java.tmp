package entity;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.IncrementValue;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Version;

import java.util.Date;

/**
 * エラー発生用GOODS_MSTテーブルのエンティティクラス
 * @author simpleJdbc (AutoGen)
 */
public class GoodsMstErrorNoTable {

	@PrimaryKey
	@IncrementValue(sequence="SEQ_GOODS_ID")
	@Column(name="GOODS_ID")
	public Integer goodsId;

	@Column(name="PRICE")
	public Double price;

	@Column(name="GOODS_NAME")
	public String goodsName;

	@Column(name="REGIST_DATE")
	public Date registDate;

	@Column(name="UPDATE_DATE")
	public Date updateDate;

	@Version
	@Column(name="VERSION")
	public String version;
}
