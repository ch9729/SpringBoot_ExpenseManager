package mysite.expense.service;

import lombok.RequiredArgsConstructor;
import mysite.expense.dto.ExpenseDTO;
import mysite.expense.entity.Expense;
import mysite.expense.repository.ExpenseRepository;
import mysite.expense.util.DataTimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        expenseDTO.setDataString(DataTimeUtil.convertDateString(expense.getDate()));
        // ExpenseDTO expenseDTO = new ExpenseDTO();   // 객체 생성
        // expenseDTO.setId(expense.getId());
        // expenseDTO.setExpenseId(expense.getExpenseId());
        // expenseDTO.setAmount(expense.getAmount());
        // expenseDTO.setName(expense.getName());
        // expenseDTO.setDescription(expense.getDescription());
        // expenseDTO.setDate(expense.getDate());
        return expenseDTO;
    }

    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) throws ParseException{
        Expense expense = mapToEntity(expenseDTO);
        expense = expRepo.save(expense);
        return mapToDTO(expense);
        }

    private Expense mapToEntity(ExpenseDTO expenseDTO) throws ParseException {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        // 1. expenseId 입력(유니크 문자열 자동생성), 업데이트 경우 id를 만들면 안되서 id값이 null 일경우에만 생성
        if(expenseDTO.getId() == null) {
            expense.setExpenseId(UUID.randomUUID().toString());
        }
        // 2. date 입력("2024-12-17" => 자바 sql로 변경)
        expense.setDate(DataTimeUtil.convertStringToDate(expenseDTO.getDataString()));
        return expense;
    }

    public void deleteExpense(String id) {
         Expense expense = expRepo.findByExpenseId(id).orElseThrow(
                                 () -> new RuntimeException("해당 ID를 찾을수 없습니다."));

        expRepo.delete(expense);
    }
    
    // expenseId로 수정할 expense 찾아서 DTO변환하여 리턴
    public ExpenseDTO getExpenseById(String id) {
        Expense expense = expRepo.findByExpenseId(id).orElseThrow(
                              () -> new RuntimeException("해당 ID를 찾을수 없습니다."));
        ExpenseDTO expenseDTO = mapToDTO(expense);
        return expenseDTO;  //DTO 변환
    }

    public List<ExpenseDTO> getFilterExpenses(String keyword, String sortBy) {
        List<Expense> list = expRepo.findByNameContainingOrDescriptionContaining(keyword, keyword);
        List<ExpenseDTO> listDTO = list.stream()    // 스트림으로 변환
                .map(this::mapToDTO) // mapToDTO로 모두 변환
                .collect(Collectors.toList());   // 다시 리스트로

        //날짜 또는 가격으로 정렬
        if(sortBy.equals("date")) {
            listDTO.sort(((o1,o2) -> o2.getDate().compareTo(o1.getDate())));
        } else {
            listDTO.sort(((o1,o2) -> o2.getAmount().compareTo(o1.getAmount())));
        }

        return listDTO;
    }
}



