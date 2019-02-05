package decorators;

import domain.Vaste;
import org.apache.commons.lang.time.DateUtils;
import org.displaytag.decorator.TableDecorator;

import java.util.Date;

public class VasteDecorator extends TableDecorator {

		@Override
		public String addRowClass() {
			Vaste vaste = (Vaste) getCurrentRowObject();
			String result;
			if(DateUtils.addMonths(new Date(),-1).before(vaste.getPublicationMoment())){
				result= "one-month";
			}else if (DateUtils.addMonths(new Date(),-2).before(vaste.getPublicationMoment())){
				result= "two-months";
			}else{
				result= "three-months";
			}

			return result;
		}


}
