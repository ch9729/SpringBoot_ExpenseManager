package mysite.expense.service;

import lombok.RequiredArgsConstructor;
import mysite.expense.dto.ExpenseDTO;
import mysite.expense.entity.Expense;
import mysite.expense.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    // final이 붙은 필드로 생성자를 만들어준다.
public class ExpenseService {
    
    private final ExpenseRepository expRepo;
    private final ModelMapper modelMapper;
    
    // @Autowired 대신 생성자 주입으로 하는 것이 효과적
    // public ExpenseService(ExpenseRepository expRepo) {
    //     this.expRepo = expRepo;
    // }

    //모든 비용 리스트를 가져온다.
    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> list = expRepo.findAll();
        List<ExpenseDTO> listDTO = list.stream()    // 스트림으로 변환
                                   .map(this::mapToDTO) // mapToDTO로 모두 변환
                                   .collect(Collectors.toList());   // 다시 리스트로
        return listDTO;
    }

    // 엔티티 -> DTO 변환(값을 전달)
    private ExpenseDTO mapToDTO(Expense expense) {
        ExpenseDTO expenseDTO = modelMapper.map(expense, ExpenseDTO.class); //modelmapper 활용
        // ExpenseDTO expenseDTO = new ExpenseDTO();   // 객체 생성
        // expenseDTO.setId(expense.getId());
        // expenseDTO.setExpenseId(expense.getExpenseId());
        // expenseDTO.setAmount(expense.getAmount());
        // expenseDTO.setName(expense.getName());
        // expenseDTO.setDescription(expense.getDescription());
        // expenseDTO.setDate(expense.getDate());
        return expenseDTO;
    }
}
