package entity;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.IncrementValue;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Table;
import simplejdbc.annotation.Version;

import java.util.Date;

/**
 * GOODS_UPDATE_TRANSテーブルのエンティティクラス
 * @author simpleJdbc (AutoGen)
 */
@Table(name="GOODS_UPDATE_TRANS")
public class GoodsUpdateTransPgsql {

	@PrimaryKey
	@IncrementValue(sequence="SEQ_TRANS_ID", sql="nextval('SEQ_TRANS_ID')")
	@Column(name="TRANS_ID")
	public String transId;

	@Column(name="UPDATE_KIND")
	public String updateKind;

	@Column(name="UPDATE_DATE")
	public Date updateDate;

	@Column(name="GOODS_ID")
	public Integer goodsId;

	@Version
	@Column(name="VERSION")
	public String version;

}
