package entity;

import simplejdbc.annotation.Column;
import simplejdbc.annotation.IncrementValue;
import simplejdbc.annotation.PrimaryKey;
import simplejdbc.annotation.Table;
import simplejdbc.annotation.Version;

import java.math.BigDecimal;
import java.util.Date;

/**
 * GOODS_MSTテーブルのエンティティクラス(MySQL用)
 * @author simpleJdbc (AutoGen)
 */
@Table(name="GOODS_MST")
public class GoodsMstMysql {

	@PrimaryKey
	@IncrementValue(sequence="AUTO_INCREMENT")
	@Column(name="GOODS_ID")
	public Integer goodsId;

	@Column(name="PRICE")
	public BigDecimal price;

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
