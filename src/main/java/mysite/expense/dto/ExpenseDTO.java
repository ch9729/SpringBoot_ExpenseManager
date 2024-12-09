package mysite.expense.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.sql.Date;
//import java.util.Date;

@Data   // Get,Set + toString
@AllArgsConstructor // 전체 필드 생성자
@NoArgsConstructor  //기본 생성자
public class ExpenseDTO {

    private Long id;
    private String expenseId;
    private String name;
    private String description;
    private Long amount;
    private Date date;
    private String dataString;  //날짜를 입력받을때 사용
}
