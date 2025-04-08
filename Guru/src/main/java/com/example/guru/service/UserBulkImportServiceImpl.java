package com.example.guru.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import com.example.guru.dto.UserBulkImportSearchDetail;
import com.example.guru.entity.ImportHistory;
import com.example.guru.entity.MGeneric;
import com.example.guru.entity.MGenericId;
import com.example.guru.entity.MUser;
import com.example.guru.form.UserBulkImportSearchForm;
import com.example.guru.repository.ImportHistoryRepository;
import com.example.guru.repository.MGenericRepository;
import com.example.guru.repository.MUserRepository;
import com.example.guru.util.CheckUtils;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class UserBulkImportServiceImpl implements UserBulkImportService {
	
	@Autowired
	MUserRepository mUserRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
    @Autowired
    private MGenericRepository mGenericRepository;

    @Autowired
    private ImportHistoryRepository importHistoryRepository;
    
    @Value("${csv.output.path}")
    private String outputPath;

    @Override
    @Transactional
    public ImportHistory startImport(MultipartFile file, BindingResult bindingResult) {
    	
    	// ファイルの必須チェック
        if (file == null || file.isEmpty()) {
            bindingResult.addError(new FieldError("importForm", "file", "ファイルが選択されていません。"));
        }

        // ファイル形式のチェック（CSVか確認）
        if (file != null && !file.getOriginalFilename().endsWith(".csv")) {
            bindingResult.addError(new FieldError("importForm", "file", "CSVファイルのみアップロード可能です。"));
        }

        // エラーがあれば処理を中断
        if (bindingResult.hasErrors()) {
            return null;
        }
        
        // ログイン中のユーザー名を取得
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        
        ImportHistory history = new ImportHistory();
        history.setImportId(importHistoryRepository.getNextSequenceValue()); 	// 取込番号
        history.setImportType("01");									 		// インポート種類
        history.setStatus("004");           								 	// ステータス(取込中)
        history.setFileName(file.getOriginalFilename());					 	// アップロードファイル名
        history.setErrorFilePath(null); 									 	// エラーファイルのパス
        history.setImportDateTime(new Timestamp(System.currentTimeMillis()));  // 取込日時
        history.setCreateUser(currentUser);								     	// 作成日時
        history.setUpdateUser(currentUser);								     	// 更新日時
        history.setCreateDateTime(new Timestamp(System.currentTimeMillis()));  // 作成日時
        history.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));  // 更新日時
        
        // 登録
        return importHistoryRepository.save(history);
    }
    
    @Override
    @Async
    public void processImportAsync(Long importId, String filePath, String currentUser) {
    	
    	// インポート履歴情報の取得
        ImportHistory history = importHistoryRepository.findById(importId).orElseThrow();
        
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] data;
            int rowNum = 0;
            
            // CSVエラー行
            List<String[]> errorRows = new ArrayList<>();
            
            // 全CSVエラー行
            List<String[]> allErrorRows = new ArrayList<>();
            
            // 成功データ
            List<String[]> successData = new ArrayList<>();

            // CSVをループ処理
            try {
				while ((data = reader.readNext()) != null) {
					
					errorRows = new ArrayList<>();
					
				    rowNum++;
				    
				    if (rowNum == 1) {
				        // ヘッダ行はスキップ
				        continue;
				    }
				    
				    ////////////////////////////////////////////
				    // チェック処理
				    ////////////////////////////////////////////                
				    // 列数チェック
				    if (data.length != 13) {
				        errorRows.add(new String[]{String.valueOf(rowNum), "列数が13ではありません。"});
				        
				        allErrorRows.addAll(errorRows);

				        continue;
				    }
				    
				    // 登録以外の場合
				    if(!data[0].equals("")) {
				    	MUser mUser = mUserRepository.findByUserId(data[0]);
				    	
				    	if(mUser == null) {
				    		errorRows.add(new String[]{String.valueOf(rowNum), "ユーザーが見つかりません。"});
				    	}
				    }
				    
					// ユーザー名
					if(data[1].equals("")) {
						errorRows.add(new String[]{String.valueOf(rowNum), "ユーザー名は必須です。"});
					}
					if(!CheckUtils.isByteNum(data[1], 100)) {
						errorRows.add(new String[]{String.valueOf(rowNum), "ユーザー名は100バイト以内で入力してください。"});
					}
					
					// 登録の場合
				    if(data[0].equals("")) {
				    	// パスワード
				    	if(data[2].equals("")) {
				    		errorRows.add(new String[]{String.valueOf(rowNum), "登録時はパスワードは必須です。"});
				    	} else {
				    		if(!CheckUtils.isHalfSizeNum(data[2])) {
				        		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードは半角英数字のみで入力してください。"});
				        	}
				    		if(data[2].length() > 10) {
				        		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードは10文字以内で入力してください。"});
				        	}
				    	}
				    	// パスワード（確認）
				    	if(data[3].equals("")) {
				    		errorRows.add(new String[]{String.valueOf(rowNum), "登録時はパスワード（確認）は必須です。"});
				    	}
				    } else {
				    	if(!data[2].equals("")) {
				    		if(!CheckUtils.isHalfSizeNum(data[2])) {
				        		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードは半角英数字のみで入力してください。"});
				        	}
				    		if(data[2].length() > 10) {
				        		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードは10文字以内で入力してください。"});
				        	}
				    	}
				    	if(!data[2].equals(data[3])) {
				    		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードが一致しません。"});
				    	}
				    }
				    
				    // ロールID
					if(data[4].equals("")) {
						errorRows.add(new String[]{String.valueOf(rowNum), "ロールIDは必須です。"});
					} else {
						
						MGenericId mGenericId = new MGenericId();
						mGenericId.setCategory("ROLE");
						mGenericId.setCode(data[4]);
						
						Optional<MGeneric> mGeneric = mGenericRepository.findById(mGenericId);
						
						if(!mGeneric.isPresent()) {
							errorRows.add(new String[]{String.valueOf(rowNum), "ロールIDが汎用マスタに存在しません。"});
						}
					}
					// 性別
					if(data[6].equals("")) {
						errorRows.add(new String[]{String.valueOf(rowNum), "性別は必須です。"});
					} else {
						
						MGenericId mGenericId = new MGenericId();
						mGenericId.setCategory("GENDER");
						mGenericId.setCode(data[6]);
						
						Optional<MGeneric> mGeneric = mGenericRepository.findById(mGenericId);
						
						if(!mGeneric.isPresent()) {
							errorRows.add(new String[]{String.valueOf(rowNum), "性別が汎用マスタに存在しません。"});
						}
					}
					// メールアドレス
					if(!data[8].equals("")) {
						if(!CheckUtils.isValidMailAddress(data[8])) {
							errorRows.add(new String[]{String.valueOf(rowNum), "有効なメールアドレスを入力してください。"});
						} else if(data[8].length() > 100) {
							errorRows.add(new String[]{String.valueOf(rowNum), "メールアドレスは100文字以内で入力してください。"});
						}
					}
					// 郵便番号
					if(data[9].equals("")) {
						errorRows.add(new String[]{String.valueOf(rowNum), "郵便番号は必須です。"});
					} else {
						if(data[9].length() > 7) {
							errorRows.add(new String[]{String.valueOf(rowNum), "郵便番号は7文字以内で入力してください。"});
						}
					}
					// 住所1
					if(data[10].equals("")) {
						errorRows.add(new String[]{String.valueOf(rowNum), "住所1は必須です。"});
					}
					if(!CheckUtils.isByteNum(data[10], 150)) {
						errorRows.add(new String[]{String.valueOf(rowNum), "住所1は150バイト以内で入力してください。"});
					}
					// 住所2
					if(!data[11].equals("")) {
						if(!CheckUtils.isByteNum(data[11], 150)) {
				    		errorRows.add(new String[]{String.valueOf(rowNum), "住所2は150バイト以内で入力してください。"});
				    	}
					}
					// 備考
					if(!data[12].equals("")) {
						if(!CheckUtils.isByteNum(data[12], 1000)) {
				    		errorRows.add(new String[]{String.valueOf(rowNum), "備考は1000バイト以内で入力してください。"});
				    	}
					}
					
					if(errorRows.size() >= 1) {
						allErrorRows.addAll(errorRows);
					} else {
						successData.add(data);
					}
					
				    
//                // 登録の場合
//                if(data[0].equals("")) {
//                	// ユーザー名
//                	if(data[1].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "ユーザー名は必須です。"});
//                	}
//                	if(!CheckUtils.isByteNum(data[1], 100)) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "ユーザー名は100バイト以内で入力してください。"});
//                	}
//                	// パスワード
//                	if(data[2].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "登録時はパスワードは必須です。"});
//                	} else {
//                		if(!CheckUtils.isHalfSizeNum(data[2])) {
//                    		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードは半角英数字のみで入力してください。"});
//                    	}
//                		if(data[2].length() > 10) {
//                    		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードは10文字以内で入力してください。"});
//                    	}
//                	}
//                	// パスワード（確認）
//                	if(data[3].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "登録時はパスワード（確認）は必須です。"});
//                	}
//                	if(!data[2].equals(data[3])) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードが一致しません。"});
//                	}
//                	// ロールID
//                	if(data[4].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "ロールIDは必須です。"});
//                	} else {
//                		
//                		MGenericId mGenericId = new MGenericId();
//                		mGenericId.setCategory("ROLE");
//                		mGenericId.setCode(data[4]);
//                		
//                		Optional<MGeneric> mGeneric = mGenericRepository.findById(mGenericId);
//                		
//                		if(mGeneric == null) {
//                			errorRows.add(new String[]{String.valueOf(rowNum), "ロールIDが汎用マスタに存在しません。"});
//                		}
//                	}
//                	// 性別
//                	if(data[5].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "性別は必須です。"});
//                	} else {
//                		
//                		MGenericId mGenericId = new MGenericId();
//                		mGenericId.setCategory("GENDER");
//                		mGenericId.setCode(data[5]);
//                		
//                		Optional<MGeneric> mGeneric = mGenericRepository.findById(mGenericId);
//                		
//                		if(mGeneric == null) {
//                			errorRows.add(new String[]{String.valueOf(rowNum), "性別が汎用マスタに存在しません。"});
//                		}
//                	}
//                	// メールアドレス
//                	if(!data[6].equals("")) {
//                		if(!CheckUtils.isValidMailAddress(data[6])) {
//                			errorRows.add(new String[]{String.valueOf(rowNum), "有効なメールアドレスを入力してください。"});
//                		} else if(data[6].length() > 100) {
//                			errorRows.add(new String[]{String.valueOf(rowNum), "メールアドレスは100文字以内で入力してください。"});
//                		}
//                	}
//                	// 郵便番号
//                	if(data[7].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "郵便番号は必須です。"});
//                	} else {
//                		if(data[7].length() > 7) {
//                			errorRows.add(new String[]{String.valueOf(rowNum), "郵便番号は7文字以内で入力してください。"});
//                		}
//                	}
//                	// 住所1
//                	if(data[8].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "住所1は必須です。"});
//                	}
//                	if(!CheckUtils.isByteNum(data[8], 150)) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "住所1は150バイト以内で入力してください。"});
//                	}
//                	// 住所2
//                	if(!data[9].equals("")) {
//                		if(!CheckUtils.isByteNum(data[9], 150)) {
//                    		errorRows.add(new String[]{String.valueOf(rowNum), "住所2は150バイト以内で入力してください。"});
//                    	}
//                	}
//                	// 備考
//                	if(!data[10].equals("")) {
//                		if(!CheckUtils.isByteNum(data[10], 1000)) {
//                    		errorRows.add(new String[]{String.valueOf(rowNum), "備考は1000バイト以内で入力してください。"});
//                    	}
//                	}
//                } else {
//                	MUser mUser = mUserRepository.findByUserId(line);
//                	
//                	if(mUser == null) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "ユーザーが見つかりません。"});
//                	}
//                	
//                	if(!data[2].equals(data[3])) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "パスワードが一致しません。"});
//                	}
//                	
//                	// 郵便番号
//                	if(data[7].equals("")) {
//                		errorRows.add(new String[]{String.valueOf(rowNum), "郵便番号は必須です。"});
//                	} else {
//                		if(data[7].length() > 7) {
//                			errorRows.add(new String[]{String.valueOf(rowNum), "郵便番号は7文字以内で入力してください。"});
//                		}
//                	}
//                }
				    
				}
			} catch (CsvValidationException e) {
				System.out.println(e.getMessage());
			}
            
            reader.close();
            
            String fileName = null;
            
            // エラーCSV出力処理
            if(allErrorRows.size() >= 1) {
            	// 現在日時
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                
                // CSVファイル名
                fileName = outputPath + "/" + timestamp + "_user_import.csv";
                
                try (CSVWriter writer = new CSVWriter(
                        new FileWriter(fileName),
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.DEFAULT_QUOTE_CHARACTER,  // 引用符（'"'）を使用
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)) {

                    String[] header = {"行数", "エラー内容"};
                    writer.writeNext(header);
                    
                    for(String[] rows : allErrorRows) {
                        writer.writeNext(rows);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            
            // 成功データをCSV取込
            for(String[] success : successData) {
                
                // M_USER
                MUser user = new MUser();
            	
            	// 登録の場合
            	if(success[0].equals("")) {
                    user.setUserId("A" + mUserRepository.getNextSequenceValue());  	    // ユーザーIDを自動生成
                    user.setUserName(success[1]);						    			// ユーザー名
                    user.setPassword(passwordEncoder.encode(success[2]));   			// パスワードをハッシュ化
                    user.setRoleId(success[4]);							    			// ロールID
                    user.setGender(success[6]);							    			// 性別
                    user.setEmail(success[8]);								    		// メールアドレス
                    user.setPostalCode(success[9]);					    				// 郵便番号
                    user.setAddress1(success[10]);						    			// 住所1
                    user.setAddress2(success[11]);						    			// 住所2
                    user.setRemarks(success[12]);							    		// 備考
                    user.setCreateUser(currentUser);								    // 作成日時
                    user.setUpdateUser(currentUser);								    // 更新日時
                    user.setCreateDateTime(new Timestamp(System.currentTimeMillis())); // 作成日時
                    user.setUpdateDateTime(new Timestamp(System.currentTimeMillis())); // 更新日時
            	} else {
            		
            		user = mUserRepository.findByUserId(success[0]);
            		
                    // 更新処理
                    user.setUserName(success[1]);											// ユーザー名
                    
                    if (CheckUtils.isNotBlank(success[2])) {
                        user.setPassword(passwordEncoder.encode(success[2]));   			// パスワードが入力されていれば更新
                    }
                    
                    user.setRoleId(success[4]);												// ロールID
                    user.setGender(success[6]);												// 性別
                    user.setEmail(success[8]);												// メールアドレス
                    user.setPostalCode(success[9]);											// 郵便番号
                    user.setAddress1(success[10]);											// 住所1
                    user.setAddress2(success[11]);											// 住所2
                    user.setRemarks(success[12]);											// 備考
                    user.setUpdateUser(currentUser);										// 更新者
                    user.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));		// 更新日時
            	}
            	
            	mUserRepository.save(user);
            }
            
            // ステータス
            String status = successData.size() == (rowNum -1) ? "001" : successData.size() == 0 ? "002" : "003";
            
            // DB更新(インポート履歴)
            history.setStatus(status);           								 	// ステータス(取込中)
            history.setErrorFilePath(fileName); 									// エラーファイルのパス
            history.setUpdateUser(currentUser);								     	// 更新日時
            history.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));  // 更新日時
            
            importHistoryRepository.save(history);

        } catch (IOException e) {
        	e.getMessage();
        }
    }

    @Override
    public Page<UserBulkImportSearchDetail> getImportHistoryPage(UserBulkImportSearchForm searchForm, int page, int size){
    	
    	// ページ番号は0ベース
        Pageable pageable = PageRequest.of(page - 1, size);
        
        // 検索条件に基づいてインポート履歴詳細情報を取得
        return importHistoryRepository.findImportHistoryDetailsByCriteria(searchForm.getImportId(), searchForm.getStatus(), searchForm.getImportDateFrom(), searchForm.getImportDateTo(), pageable);
    }
}